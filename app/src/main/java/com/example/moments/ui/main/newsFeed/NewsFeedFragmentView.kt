package com.example.moments.ui.main.newsFeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moments.R
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.message.MessageActivityView
import com.google.firebase.firestore.DocumentSnapshot
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
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
        newsfeed_header_bar.inflateMenu(R.menu.header_newsfeeds)
        setUpOnClicked()
    }

    override fun setUp() {
        presenter.onViewPrepared()
    }

    override fun updatePost(listPost: List<DocumentSnapshot>) {
        TODO("Not yet implemented")
    }

    override fun toString(): String = "newsfeedFragment"

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    private fun setUpOnClicked() {
        newsfeed_header_bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.storyBtn -> {
                    true
                }
                R.id.msgBtn -> {
                    val intent: Intent = Intent(activity, MessageActivityView::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}