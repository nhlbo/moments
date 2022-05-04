package com.example.moments.ui.main.newsFeed

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.comment.CommentActivityView
import com.example.moments.ui.main.latestMessage.LatestMessageActivityView
import com.example.moments.ui.main.newsFeed.newPost.NewPostActivityView
import com.example.moments.ui.main.newsFeed.newPostStepTwo.NewPostActivityStepTwoView
import com.example.moments.ui.main.viewOtherProfile.OtherProfileActivityView
import kotlinx.android.synthetic.main.activity_news_feed.*
import javax.inject.Inject


class NewsFeedFragmentView : BaseFragment(), INewsFeedView, IAdapterCallBack {

    companion object {
        fun newInstance(): NewsFeedFragmentView {
            return NewsFeedFragmentView()
        }
    }

    @Inject
    internal lateinit var presenter: INewsFeedPresenter<INewsFeedView, INewsFeedInteractor>

    @Inject
    internal lateinit var layoutManager: LinearLayoutManager

    private var data: List<RetrievedPost> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.activity_news_feed, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                onActivityResult(REQUEST_VIDEO_CAPTURE, result)
            }
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
        newsfeed_header_bar.inflateMenu(R.menu.header_newsfeeds)
        initPopupMenu()
    }

    override fun setUp() {
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcNewsfeedPanel.layoutManager = layoutManager
        val adapter = NewsFeedAdapter(requireContext(), mutableListOf(), this)
        rcNewsfeedPanel.adapter = adapter
        rcNewsfeedPanel.isNestedScrollingEnabled = false

        adapter.onButtonClick = { post ->
            val intent = Intent(context, CommentActivityView::class.java)
            intent.putExtra("postId", post.id)
            startActivity(intent)
        }

        rcNewsfeedPanel.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> rcNewsfeedPanel.parent
                        ?.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })
        setUpOnClicked()
        presenter.onViewPrepared()
    }

    override fun updatePost(listPost: List<RetrievedPost>) {
        data = listPost
        (rcNewsfeedPanel.adapter as NewsFeedAdapter).updatePost(listPost)
    }

    override fun onItemTouch(position: Int, command: String) {
        when (command) {
            "viewProfile" -> {
                val intent = Intent(activity, OtherProfileActivityView::class.java)
                intent.putExtra("USER_ID", data[position].creator.id)
                startActivity(intent)
            }
            "likePost" -> {
                presenter.onLikePost(data[position].id)
            }
            "unlikePost" -> {
                presenter.onUnlikePost(data[position].id)
            }
            "savePost" -> {
                presenter.onBookmarkPost(data[position].id)
            }
            "unsavePost" -> {
                presenter.onUnBookmarkPost(data[position].id)
            }
            "showComment" -> {
                val intent = Intent(activity, CommentActivityView::class.java)
                intent.putExtra("postId", data[position].id)
                startActivity(intent)
            }
        }
    }

    override fun toString(): String = "newsfeedFragment"

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    private fun setUpOnClicked() {
        newsfeed_header_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.storyBtn -> {
                    popup.show()
                    true
                }
                R.id.msgBtn -> {
                    val intent = Intent(activity, LatestMessageActivityView::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private lateinit var popup: PopupMenu
    private fun initPopupMenu() {
        popup = PopupMenu(requireContext(), newsfeed_header_bar.getChildAt(0))
        popup.inflate(R.menu.menu_upload_selection)
        popup.setOnMenuItemClickListener(menuListener)
    }

    private val menuListener = PopupMenu.OnMenuItemClickListener {
        when (it.itemId) {
            R.id.upload_post_item -> {
                val intent = Intent(activity, NewPostActivityView::class.java)
                startActivity(intent)
                true
            }
            R.id.upload_moment_item -> {
                dispatchTakeVideoIntent()
                true
            }
            else -> false
        }
    }

    // start recording video
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val REQUEST_VIDEO_CAPTURE = 2
    private fun onActivityResult(requestCode: Int, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_VIDEO_CAPTURE -> {
                    val intent = result.data
                    val videoUri: Uri = intent?.data!!
                    val intentToStepTwo =
                        Intent(requireContext(), NewPostActivityStepTwoView::class.java)
                    intentToStepTwo.putExtra("uploadType", 1)
                    intentToStepTwo.putExtra("videoLink", getRealPathFromURI(videoUri))
                    startActivity(intentToStepTwo)
                }
            }
        }
    }

    //src: https://developer.android.com/training/camera/videobasics
    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(requireActivity().packageManager)?.also {
                startForResult.launch(takeVideoIntent)
            }
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? =
            requireContext().contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}


