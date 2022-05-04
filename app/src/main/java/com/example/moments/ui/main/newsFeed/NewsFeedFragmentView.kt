package com.example.moments.ui.main.newsFeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.comment.CommentActivityView
import com.example.moments.ui.main.latestMessage.LatestMessageActivityView
import com.example.moments.ui.main.newsFeed.newPost.NewPostActivityView
import com.example.moments.ui.main.newsFeed.sharePost.BottomSheetFragment
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
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
        newsfeed_header_bar.inflateMenu(R.menu.header_newsfeeds)
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
        if (command == "showComment") {
//            val request = NavDeepLinkRequest.Builder
//                .fromUri(R.string.commentFragment.toString().toUri())
//                .build()
//            findNavController().navigate(R.id.commentFragmentView)
        }
        if (command == "viewProfile") {
            val intent = Intent(activity, OtherProfileActivityView::class.java)
            intent.putExtra("USER_ID", data[position].creator.id)
            startActivity(intent)
        }
        if(command == "sharePost"){
            val fakeData = arrayListOf<User>()
            fakeData.add(User("","An Duy","","https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4",""))
            fakeData.add(User("","Son Tran","","https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4",""))
            fakeData.add(User("","Hoang Long","","https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4",""))
            fakeData.add(User("","Quang Huy","","https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4",""))
            fakeData.add(User("","An Duy","","https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4",""))
            val bottomSheetFragment = BottomSheetFragment(fakeData)
            getFragmentManager()?.let { bottomSheetFragment.show(it, bottomSheetFragment.tag) }

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
                    val intent = Intent(activity, NewPostActivityView::class.java)
                    startActivity(intent)
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

}