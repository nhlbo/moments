package com.example.moments.ui.customClasses

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moments.R
import com.example.moments.ui.main.HomeFragmentView
import com.example.moments.ui.main.comment.CommentFragmentView

class ViewPagerAdapter(private val fragments: List<Fragment>,private val fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun replaceFragment(fragment: Fragment?) {
        if(fragmentManager.backStackEntryCount > 5) fragmentManager.popBackStack()
        val fragmentManager: FragmentManager = fragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment!!, fragment.toString())
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.commit()
    }
}