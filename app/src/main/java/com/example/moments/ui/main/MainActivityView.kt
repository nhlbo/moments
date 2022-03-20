package com.example.moments.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.ui.custom_classes.ViewPagerAdapter
import com.example.moments.ui.moments.MomentsFragmentView
import com.example.moments.ui.feed.FeedFragmentView
import com.example.moments.ui.notification.NotificationFragmentView
import com.example.moments.ui.search.SearchFragmentView
import com.example.moments.ui.profile.ProfileFragmentView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivityView : AppCompatActivity() {
    //5 main fragments
    private lateinit var feedActivity: FeedFragmentView
    private lateinit var searchActivity: SearchFragmentView
    private lateinit var momentsFragmentView: MomentsFragmentView
    private lateinit var notificationActivity: NotificationFragmentView
    private lateinit var profileActivity: ProfileFragmentView

    // navigation bar and page viewer
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager: ViewPager2

    // temp list for saving the 3 latest pages recently selected
    private var backStackIndexList: IntArray = IntArray(3)
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //bottom navigation bar
        initBottomNavigationBar()
        //View pager for displaying fragments
        initViewPager()

        //init four main fragments
        feedActivity = FeedFragmentView()
        searchActivity = SearchFragmentView()
        momentsFragmentView = MomentsFragmentView()
        notificationActivity = NotificationFragmentView()
        profileActivity = ProfileFragmentView()

       // setCurrentFragment(newsFeedActivity)
        setupViewPager()
    }

    override fun onBackPressed() {
        try {
            if(currentPosition >= backStackIndexList.size) currentPosition--

            backStackIndexList[currentPosition] = -1
            val index = backStackIndexList[currentPosition - 1]
            replaceFragment(index)
            currentPosition--
        }
        catch (e:IndexOutOfBoundsException){
            super.onBackPressed()
        }
    }

    private fun setupViewPager(){
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        //add 5 main fragments to adapter
        adapter.addFragment(feedActivity, "NewsfeedTab")
        adapter.addFragment(searchActivity, "SearchTab")
        adapter.addFragment(momentsFragmentView, "Moments")
        adapter.addFragment(notificationActivity, "NotificationTab")
        adapter.addFragment(profileActivity, "ProfileTab")

        //set adapter to page view
        viewPager.adapter = adapter
        viewPager.currentItem = 0
    }

    private fun initViewPager(){
        viewPager = findViewById(R.id.fragmentContainerView)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position:Int, positionOffset:Float, positionOffsetPixels:Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                when (position) {
                    0 -> bottomNavigationView.menu.findItem(R.id.newsFeedFragmentView).isChecked = true
                    1 -> bottomNavigationView.menu.findItem(R.id.searchFragmentView).isChecked = true
                    2 -> bottomNavigationView.menu.findItem(R.id.momentsFragmentView).isChecked = true
                    3 -> bottomNavigationView.menu.findItem(R.id.notificationFragmentView).isChecked = true
                    4 -> bottomNavigationView.menu.findItem(R.id.profileFragmentView).isChecked = true
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //push position to list for waiting to be called later
                pushSelectedPage(position)
            }
        })


    }

    private fun initBottomNavigationBar(){
        bottomNavigationView = findViewById(R.id.bottomNavigationBar)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.newsFeedFragmentView->replaceFragment(0)
                R.id.searchFragmentView->replaceFragment(1)
                R.id.momentsFragmentView->replaceFragment(2)
                R.id.notificationFragmentView->replaceFragment(3)
                R.id.profileFragmentView->replaceFragment(4)
            }
            true
        }
    }

    private fun pushSelectedPage(position:Int){
        if(currentPosition >= backStackIndexList.size){
            val queue: Queue<Int> = backStackIndexList.toCollection(LinkedList())
            queue.remove()
            queue.add(position)
            backStackIndexList = queue.toIntArray()
        }
        else {
            backStackIndexList[currentPosition] = position
            currentPosition++
        }
    }

    private fun replaceFragment(index:Int){
        viewPager.setCurrentItem(index,false)
    }
}