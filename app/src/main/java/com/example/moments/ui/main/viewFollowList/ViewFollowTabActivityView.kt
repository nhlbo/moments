package com.example.moments.ui.main.viewFollowList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewFollowTabActivityView : AppCompatActivity() {
    private var viewPager: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_follow_list)
        initFollowLinear()
    }

    private fun initFollowLinear() {
        val followersDataList = arrayListOf<Followers>()
        val urlAvatar =
            "https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107"
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", true))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))
        followersDataList.add(Followers(urlAvatar, "AnDuy", false))

        val followingDataList = arrayListOf<Following>()
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))
        followingDataList.add(Following(urlAvatar, "an_duy_dao", "An Duy"))

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout_view_follow)
        val intent = intent
        val type = intent.getStringExtra("FollowViewType")?.toInt()
        viewPager = findViewById(R.id.vp2_view_follow)
        viewPager?.adapter = FollowGridViewPagerAdapter(
            this, followersDataList, followingDataList
        )

        var x = -1
        if (type == 0) x = 0
        else if (type == 1) x = 1
        viewPager?.setCurrentItem(x)
        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.text = "${followersDataList.size} Followers"
                1 -> tab.text = "${followingDataList.size} Following"
            }
        }.attach()
    }
}