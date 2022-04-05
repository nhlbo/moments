package com.example.moments.ui.main.newsFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.ui.main.comment.CommentFragmentView

class NewsFeedFragmentView : Fragment() {
    private var toolBar: Toolbar? = null
    private lateinit var parentViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar = getView()?.findViewById(R.id.newsfeed_header_bar)
        toolBar?.inflateMenu(R.menu.header_newsfeeds)
        onItemSelected()
        parentViewPager = activity?.findViewById(R.id.fragmentContainerView)!!

    }

    private fun onItemSelected() {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.storyBtn -> {
                    // Navigate to settings screen
                    switchFragment(CommentFragmentView())
                    true
                }
                R.id.msgBtn -> {
                    // Save profile changes
                    parentViewPager.currentItem += 1
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment){
        activity?.supportFragmentManager?.beginTransaction()!!
            .replace(R.id.navigateFragmentsContainer, fragment,fragment.toString())
            .addToBackStack(fragment.toString())
            .commit()
    }

    override fun toString(): String = "newsfeedFragment"
}