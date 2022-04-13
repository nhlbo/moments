package com.example.moments.ui.main

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.ui.customClasses.ViewPagerAdapter
import com.example.moments.ui.customClasses.onChildFragmentClick


class MainActivityView : AppCompatActivity(), onChildFragmentClick {
    // navigation bar and page viewer
    private lateinit var viewPager: ViewPager2
    private lateinit var homeFragmentView: HomeFragmentView
    private lateinit var subActivitiesFragmentView: SubActivitiesFragmentView
    private lateinit var bottomNavigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFragmentView = HomeFragmentView()
        subActivitiesFragmentView = SubActivitiesFragmentView()
        //View pager for displaying fragments
        initViewPager()
        //init four main fragments

        // setCurrentFragment(newsFeedActivity)
        //setupViewPager()
    }


    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

//    private fun setupViewPager(){
//        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
//
//        //add 5 main fragments to adapter
//        adapter.addFragment(newsFeedActivity, "NewsfeedTab")
//        adapter.addFragment(searchActivity, "SearchTab")
//        adapter.addFragment(momentsFragmentView, "Moments")
//        adapter.addFragment(notificationActivity, "NotificationTab")
//        adapter.addFragment(profileActivity, "ProfileTab")
//
//        //set adapter to page view
//    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.fragmentViewPager)
        val list = listOf(homeFragmentView, subActivitiesFragmentView)
        viewPager.adapter = ViewPagerAdapter(list, supportFragmentManager, lifecycle)
    }

    override fun onChildButtonClicked(view: View?) {
        when (view?.id) {
            R.id.msgBtn -> viewPager.currentItem + 1
        }
    }

//    private fun initBottomNavigationBar(){
//        bottomNavigationView = findViewById(R.id.bottomNavigationBar)
//
//        bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.newsFeedFragmentView->replaceFragment(0)
//                R.id.searchFragmentView->replaceFragment(1)
//                R.id.momentsFragmentView->replaceFragment(2)
//                R.id.notificationFragmentView->replaceFragment(3)
//                R.id.profileFragmentView->replaceFragment(4)
//            }
//            true
//        }
//    }

//    private fun pushSelectedPage(position:Int){
//        if(currentPosition >= backStackIndexList.size){
//            val queue: Queue<Int> = backStackIndexList.toCollection(LinkedList())
//            queue.remove()
//            queue.add(position)
//            backStackIndexList = queue.toIntArray()
//        }
//        else {
//            backStackIndexList[currentPosition] = position
//            currentPosition++
//        }
//    }

//    private fun replaceFragment(index:Int){
//        viewPager.setCurrentItem(index,false)
//    }
}