package com.example.moments.ui.main.newsFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moments.R
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.comment.CommentFragmentView
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_news_feed.*
import javax.inject.Inject

class NewsFeedFragmentView : BaseFragment(), INewsFeedView {

    companion object {
        fun newInstance(): NewsFeedFragmentView {
            return NewsFeedFragmentView()
        }
    }

    @Inject
    internal lateinit var presenter: INewsFeedPresenter<INewsFeedView, INewsFeedInteractor>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.activity_news_feed, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsfeed_header_bar.inflateMenu(R.menu.header_newsfeeds)
        onItemSelected()
    }

    override fun setUp() {
        presenter.onViewPrepared()
    }

    private fun onItemSelected() {
        newsfeed_header_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.storyBtn -> {
                    // Navigate to settings screen
                    switchFragment(CommentFragmentView())
                    true
                }
                R.id.msgBtn -> {
                    // Save profile changes
                    fragmentContainerView.currentItem += 1
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()!!
            .replace(R.id.fragmentViewPager, fragment, fragment.toString())
            .addToBackStack(fragment.toString())
            .commit()
    }

    override fun updatePost(listPost: List<DocumentSnapshot>) {
        TODO("Not yet implemented")
    }

    override fun toString(): String = "newsfeedFragment"

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}