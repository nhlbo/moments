package com.example.moments.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.moments.R
import com.example.moments.ui.newsFeed.NewsFeedActivityView
import com.example.moments.ui.newsFeed.NewsFeedFragmentView
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
        val firstFragment=NewsFeedFragmentView()
        val secondFragment=ProfileFragmentView()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.newsFeedFragmentView->setCurrentFragment(firstFragment)
                R.id.profileFragmentView->setCurrentFragment(secondFragment)
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