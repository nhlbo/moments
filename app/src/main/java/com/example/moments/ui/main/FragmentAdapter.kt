package com.example.moments.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moments.ui.main.moments.MomentsFragmentView
import com.example.moments.ui.main.newsFeed.NewsFeedFragmentView
import com.example.moments.ui.main.notification.NotificationFragmentView
import com.example.moments.ui.main.search.SearchFragmentView
import com.example.moments.ui.main.viewProfile.ProfileFragmentView

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var tabCount = 0

    internal fun setCount(count: Int) {
        tabCount = count
    }

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFeedFragmentView.newInstance()
            1 -> SearchFragmentView.newInstance()
            2 -> MomentsFragmentView.newInstance()
            3 -> NotificationFragmentView.newInstance()
            4 -> ProfileFragmentView.newInstance()
            else -> NewsFeedFragmentView.newInstance()
        }
    }
}