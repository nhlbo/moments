package com.example.moments.ui.main.viewFollowList

import android.os.Bundle
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.viewProfile.ProfileFragmentView
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
        presenter.onPerformQueryFollowerCurrentUser()
        presenter.onPerformQueryFollowingCurrentUser()

        val intent = intent
        val type = intent.getStringExtra("FollowViewType")?.toInt()
        val user = intent.getParcelableExtra<User>(ProfileFragmentView.USER_KEY)!!

        tbFollowList.setNavigationOnClickListener { finish() }
        tvToolbarTittle.setText(user.username)
        vp2_view_follow.adapter = FollowGridViewPagerAdapter(
            this, followersDataList, followingDataList
        )

        type?.let { vp2_view_follow?.setCurrentItem(it) }
        TabLayoutMediator(tab_layout_view_follow, vp2_view_follow!!) { tab, position ->
            when (position) {
                0 -> tab.text = "${followersDataList.size} Followers"
                1 -> tab.text = "${followingDataList.size} Following"
            }
        }.attach()
    }

    override fun addFollowerUsers(users: List<User>) {
        for (user in users) {
            followersDataList.add(Followers(user.avatar, user.username, true))
        }
    }

    override fun addFollowingUsers(users: List<User>) {
        for (user in users) {
            followingDataList.add(Following(user.avatar, user.username, user.email))
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}