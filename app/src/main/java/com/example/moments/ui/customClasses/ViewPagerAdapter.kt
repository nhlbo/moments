package com.example.moments.ui.customClasses

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
    private val mFragmentList : ArrayList<Fragment>  = arrayListOf()
    val mTagList : ArrayList<String?> = arrayListOf()

    override fun getItemCount(): Int = mFragmentList.size
    override fun createFragment(position: Int): Fragment = mFragmentList[position]

    fun getFragmentIndex(tag:String) : Int = mTagList.indexOf(tag)


    fun addFragment(fragment:Fragment){
        mFragmentList.add(fragment)
        mTagList.add(null)
    }

    fun addFragment(fragment: Fragment, tag:String){
        mFragmentList.add(fragment)
        mTagList.add(tag)
    }
}