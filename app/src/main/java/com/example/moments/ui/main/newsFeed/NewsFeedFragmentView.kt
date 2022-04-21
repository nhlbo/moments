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
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.latestMessage.LatestMessageActivityView
import com.example.moments.ui.main.newsFeed.post.PostView
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
        rcNewsfeedPanel.adapter = context?.let { NewsFeedAdapter(it, mutableListOf(), this) }
        rcNewsfeedPanel.isNestedScrollingEnabled = false
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
        (rcNewsfeedPanel.adapter as NewsFeedAdapter).updatePost(listPost)
    }

    override fun onItemTouch(position: Int, command: String) {
        if (command == "showComment") {
//            val request = NavDeepLinkRequest.Builder
//                .fromUri(R.string.commentFragment.toString().toUri())
//                .build()
//            findNavController().navigate(R.id.commentFragmentView)
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
                    val intent = Intent(activity, PostView::class.java)
                    startActivity(intent)
                    true
                }
                R.id.msgBtn -> {
                    val intent: Intent = Intent(activity, LatestMessageActivityView::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

}