package com.example.moments.ui.main.viewOtherProfile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.chat.ChatActivityView
import com.example.moments.ui.main.latestMessage.LatestMessageActivityView
import com.example.moments.ui.main.viewFollowList.ViewFollowTabActivityView
import com.example.moments.ui.main.viewProfile.IProfileView
import com.example.moments.ui.main.viewProfile.MediaGridViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_other_profile.*
import kotlinx.android.synthetic.main.activity_view_profile.*
import javax.inject.Inject

class OtherProfileActivityView : BaseActivity(), IOtherProfileActivityView {
    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    @Inject
    internal lateinit var presenter: IOtherProfileActivityPresenter<IOtherProfileActivityView, IOtherProfileActivityInteractor>

    private var viewPager: ViewPager2? = null

    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_profile)
        presenter.onAttach(this)
        initMediaGrid()
        initLayout()
        toolBarAction()
        buttonsAction()
        // Lấy userId truyền trong intent
        // presenter.onViewPrepared(userId)
    }

    private fun initMediaGrid() {
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout_view_other_profile)
        viewPager = findViewById(R.id.vp2_view_other_profile)
        viewPager?.adapter = MediaGridViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid)
                1 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_video)
            }
        }.attach()
    }


    private fun initToolBar() {
//        toolBar = view?.findViewById(R.id.profile_header_toolbar)
//        toolBar?.inflateMenu(R.menu.header_profile)
//        toolBar?.title = "Your profile"
//        onItemSelected()
    }

    private fun initLayout() {
        val linearLayoutFollowers = findViewById<LinearLayout>(R.id.llOtherFollowers)
        val linearLayoutFollowing = findViewById<LinearLayout>(R.id.llOtherFollowing)

        linearLayoutFollowers.setOnClickListener{
            val intent = Intent(this, ViewFollowTabActivityView::class.java)
            intent.putExtra("FollowViewType", "0")
            startActivity(intent)
        }
        linearLayoutFollowing.setOnClickListener{
            val intent: Intent = Intent(this, ViewFollowTabActivityView::class.java)
            intent.putExtra("FollowViewType", "1")
            startActivity(intent)
        }
    }

    private fun toolBarAction(){
        otherProfileToolbar.setNavigationOnClickListener { finish() }
        otherProfileToolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.moreOption_otherProfile -> {
                    // pop menu (block, ...)
                    true
                }
                else -> false
            }
        }
    }

    private fun buttonsAction(){
        btnFollowOtherProfile.setOnClickListener {
            //TODO Follow or unfollow
        }
        btnMessageOtherProfile.setOnClickListener {
            val intent = Intent(this, ChatActivityView::class.java)
            intent.putExtra(LatestMessageActivityView.USER_KEY, userModel)
            startActivity(intent)
            finish()
        }
    }

    override fun getCurrentUserModel(user: User) {
        tvOtherUsernameProfile.text = user.username
        tvOtherHashtagProfile.text = user.email
        tvOtherBioProfile.text = user.bio
        userModel = user
    }
}