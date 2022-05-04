package com.example.moments.ui.main.viewFollowList

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.viewProfile.ProfileFragmentView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_view_follow_list.*
import javax.inject.Inject

class ViewFollowListActivityView : BaseActivity(), IViewFollowListActivityView {
    @Inject
    lateinit var presenter: IViewFollowListActivityPresenter<IViewFollowListActivityView, IViewFollowListActivityInteractor>

    private var followersDataList = arrayListOf<Followers>()
    private var followingDataList = arrayListOf<Following>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_follow_list)
        presenter.onAttach(this)
        initFollowLinear()
    }

    private fun initFollowLinear() {
        val intent = intent
        val type = intent.getStringExtra("FollowViewType")?.toInt()
        val user = intent.getParcelableExtra<User>(ProfileFragmentView.USER_KEY)!!

        tbFollowList.setNavigationOnClickListener { finish() }
        tvToolbarTittle.text = user.username

        vp2_view_follow.adapter = FollowGridViewPagerAdapter(
            supportFragmentManager,lifecycle, followersDataList, followingDataList
        )
        vp2_view_follow.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(p0: View?) {
                vp2_view_follow.offscreenPageLimit = 1
            }

            override fun onViewDetachedFromWindow(p0: View?) {}
        })

        TabLayoutMediator(tab_layout_view_follow, vp2_view_follow!!) { tab, position ->
            when (position) {
                0 -> tab.text = "${followersDataList.size} Followers"
                1 -> tab.text = "${followingDataList.size} Following"
            }
        }.attach()
        presenter.onPerformQueryFollowerCurrentUser()
        presenter.onPerformQueryFollowingCurrentUser()
        type?.let { vp2_view_follow?.setCurrentItem(it) }

    }

    override fun addFollowerUsers(users: List<User>) {
        followersDataList.clear()
        for (user in users) {
            followersDataList.add(Followers(user.id, user.avatar, user.username, true))
        }
        val followerFragment = supportFragmentManager.findFragmentByTag("f" + 0) as LinearFollowerFragment
        followerFragment.updateList(followersDataList)
        (tab_layout_view_follow.getTabAt(0) as TabLayout.Tab).text = "${followersDataList.size} Followers"
    }

    override fun addFollowingUsers(users: List<User>) {
        followingDataList.clear()
        for (user in users) {
            followingDataList.add(Following(user.id, user.avatar, user.username, user.email))
        }
        val followingFragment = supportFragmentManager.findFragmentByTag("f" + 1) as LinearFollowingFragment
        followingFragment.updateList(followingDataList)
        (tab_layout_view_follow.getTabAt(1) as TabLayout.Tab).text = "${followingDataList.size} Following"
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}