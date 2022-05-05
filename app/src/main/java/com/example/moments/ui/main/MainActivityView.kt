package com.example.moments.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class MainActivityView : BaseActivity(), HasAndroidInjector, IMainActivityView {

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private lateinit var viewPagerAdapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewPagerAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPagerAdapter.setCount(5)
        setUp()
    }

    override fun onBackPressed() {
        if (fragmentViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            fragmentViewPager.currentItem = fragmentViewPager.currentItem - 1
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun setUp() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navFeedMenu -> switchPage(0)
                R.id.navSearchMenu -> switchPage(1)
                R.id.navMomentMenu -> switchPage(2)
                R.id.navNotificationMenu -> switchPage(3)
                R.id.navProfileMenu -> switchPage(4)
            }
            true
        }

        fragmentViewPager.adapter = viewPagerAdapter
        fragmentViewPager.offscreenPageLimit = 3
        fragmentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationView.menu.findItem(R.id.navFeedMenu).isChecked = true
                    1 -> bottomNavigationView.menu.findItem(R.id.navSearchMenu).isChecked = true
                    2 -> bottomNavigationView.menu.findItem(R.id.navMomentMenu).isChecked = true
                    3 -> bottomNavigationView.menu.findItem(R.id.navNotificationMenu).isChecked = true
                    4 -> bottomNavigationView.menu.findItem(R.id.navProfileMenu).isChecked = true
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
    private fun switchPage(navigatePosition: Int){
        fragmentViewPager.postDelayed({ fragmentViewPager.currentItem = navigatePosition }, 10)
    }
}