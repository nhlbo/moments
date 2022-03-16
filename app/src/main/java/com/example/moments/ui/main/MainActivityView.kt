package com.example.moments.ui.main

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.moments.R
import com.example.moments.ui.newsFeed.NewsFeedActivityView
import com.example.moments.ui.newsFeed.NewsFeedFragmentView
import com.example.moments.ui.notification.NotificationFragmentView
import com.example.moments.ui.search.SearchFragmentView
import com.example.moments.ui.viewProfile.ProfileFragmentView
import com.example.moments.ui.viewProfile.ViewProfileActivityView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarMenuView
import com.google.android.material.navigation.NavigationBarView

class MainActivityView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        val firstFragment = NewsFeedFragmentView()
        val secondFragment = SearchFragmentView()
        val thirdFragment = NotificationFragmentView()
        val fourthFragment = ProfileFragmentView()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.newsFeedFragmentView->setCurrentFragment(firstFragment)
                R.id.searchFragmentView->setCurrentFragment(secondFragment)
                R.id.notificationFragmentView->setCurrentFragment(thirdFragment)
                R.id.profileFragmentView->setCurrentFragment(fourthFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }

}