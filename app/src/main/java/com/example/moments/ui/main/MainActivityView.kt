package com.example.moments.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class MainActivityView : BaseActivity(), HasAndroidInjector,
    BottomNavigationView.OnNavigationItemSelectedListener, IMainActivityView {

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    private lateinit var viewPagerAdapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewPagerAdapter = FragmentAdapter(supportFragmentManager)
        setUp()
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
        fragmentViewPager.adapter = viewPagerAdapter
        fragmentViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationView.menu.findItem(R.id.navFeedMenu).isChecked = true
                    1 -> bottomNavigationView.menu.findItem(R.id.navSearchMenu).isChecked = true
                    2 -> bottomNavigationView.menu.findItem(R.id.navMomentMenu).isChecked = true
                    3 -> bottomNavigationView.menu.findItem(R.id.navNotificationMenu)
                        .isChecked = true
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navFeedMenu -> fragmentViewPager.currentItem = 0
            R.id.navSearchMenu -> fragmentViewPager.currentItem = 1
            R.id.navMomentMenu -> fragmentViewPager.currentItem = 2
            R.id.navNotificationMenu -> fragmentViewPager.currentItem = 3
            R.id.navProfileMenu -> fragmentViewPager.currentItem = 4
        }
        return true
    }
}