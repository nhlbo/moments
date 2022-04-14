package com.example.moments.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.moments.ui.main.newsFeed.NewsFeedFragmentView
import com.example.moments.ui.main.search.SearchFragmentView

class FragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    private var tabCount = 0

    override fun getCount(): Int = tabCount

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> NewsFeedFragmentView.newInstance()
            else -> SearchFragmentView()
        }
    }

    internal fun setCount(count: Int) {
        tabCount = count
    }
}