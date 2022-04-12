package com.example.moments.ui.main.viewProfile

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.ThemeUtils
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.ui.forgetPassword.ForgetPasswordActivityView
import com.example.moments.ui.main.viewFollowList.ViewFollowTabActivityView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragmentView : Fragment(R.layout.activity_view_profile) {
    private var toolBar: Toolbar? = null
    private var viewPager: ViewPager2? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_view_profile, container, false)
        initToolBar()
        initMediaGrid(view)
        initLayout(view)
        return view
    }

    private fun initMediaGrid(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout_view_profile)
        viewPager = view.findViewById(R.id.vp2_view_profile)
        viewPager?.adapter = MediaGridViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid)
                1 -> tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_video)
            }
        }.attach()
    }

    private fun initToolBar() {
        toolBar = view?.findViewById(R.id.profile_header_toolbar)
        toolBar?.inflateMenu(R.menu.header_profile)
        toolBar?.title = "Your profile"
        onItemSelected()
    }

    private fun initLayout(view: View) {
        var linearLayoutFollowers = view.findViewById<LinearLayout>(R.id.llFollowers)
        var linearLayoutFollowing = view.findViewById<LinearLayout>(R.id.llFollowing)

        linearLayoutFollowers.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ViewFollowTabActivityView::class.java)
            intent.putExtra("FollowViewType","0")
            startActivity(intent)
        })
        linearLayoutFollowing.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(context, ViewFollowTabActivityView::class.java)
            intent.putExtra("FollowViewType","1")
            startActivity(intent)
        })
    }

    private fun onItemSelected() {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profileSettingBtn -> {
                    // Navigate to settings screen
                    true
                }
                R.id.storyBtn -> {
                    // Save profile changes

                    true
                }
                else -> false
            }
        }
    }
}