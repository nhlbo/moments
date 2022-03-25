package com.example.moments.ui.newsFeed

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.moments.R
import com.example.moments.ui.comment.CommentFragmentView
import com.example.moments.ui.custom_classes.FragmentChangeListener




class NewsFeedFragmentView : Fragment() {
    private var toolBar: Toolbar? = null
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
    }

    private fun onItemSelected() {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.storyBtn -> {
                    // Navigate to settings screen
                    true
                }
                R.id.msgBtn -> {
                    // Save profile changes
                    val fr: Fragment = CommentFragmentView()
                    val fc = activity as FragmentChangeListener?
                    fc!!.replaceFragment(fr)
                    true
                }
                else -> false
            }
        }
    }

}