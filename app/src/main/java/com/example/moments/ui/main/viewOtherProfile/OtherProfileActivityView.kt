package com.example.moments.ui.main.viewOtherProfile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.OtherUser
import com.example.moments.data.model.Post
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener
import com.example.moments.ui.main.chat.ChatActivityView
import com.example.moments.ui.main.latestMessage.LatestMessageActivityView
import com.example.moments.ui.main.viewFollowList.ViewFollowListActivityView
import com.example.moments.ui.main.viewPost.ViewPostActivityView
import com.example.moments.ui.main.viewProfile.GridMediaFragment
import com.example.moments.ui.main.viewProfile.MediaGridViewPagerAdapter
import com.example.moments.ui.main.viewProfile.ProfileFragmentView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_other_profile.*
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

    private lateinit var userModel: OtherUser
    private lateinit var userPosts: List<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_profile)
        presenter.onAttach(this)
        initMediaGrid()
        initLayout()
        toolBarAction()
        buttonsAction()

        // Lấy userId truyền trong intent
        val userId = intent.getStringExtra("USER_ID")!!
        presenter.onViewPrepared(userId)
    }


    private val onPostClickListener = object : IOnRecyclerViewItemTouchListener {
        override fun onItemClick(position: Int) {
            val intent = Intent(this@OtherProfileActivityView, ViewPostActivityView::class.java)
            intent.putExtra("postId", userPosts[position].id)
            startActivity(intent)
        }
    }
    private val onVideoClickListener = object : IOnRecyclerViewItemTouchListener {
        override fun onItemClick(position: Int) {

        }
    }

    private fun initMediaGrid() {
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout_view_other_profile)
        viewPager = findViewById(R.id.vp2_view_other_profile)

        viewPager?.adapter = MediaGridViewPagerAdapter(
            supportFragmentManager,
            lifecycle,
            onPostClickListener,
            onVideoClickListener
        )

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

        linearLayoutFollowers.setOnClickListener {
            val intent = Intent(this, ViewFollowListActivityView::class.java)
            intent.putExtra(ProfileFragmentView.USER_KEY, userModel)
            intent.putExtra("FollowViewType", "0")
            startActivity(intent)
        }
        linearLayoutFollowing.setOnClickListener {
            val intent = Intent(this, ViewFollowListActivityView::class.java)
            intent.putExtra(ProfileFragmentView.USER_KEY, userModel)
            intent.putExtra("FollowViewType", "1")
            startActivity(intent)
        }
    }

    private fun toolBarAction() {
        otherProfileToolbar.setNavigationOnClickListener { finish() }
        otherProfileToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.moreOption_otherProfile -> {
                    // pop menu (block, ...)
                    true
                }
                else -> false
            }
        }
    }

    private fun buttonsAction() {
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

    override fun getCurrentUserModel(user: OtherUser) {
        tvOtherUsernameProfile.text = user.username
        tvOtherHashtagProfile.text = user.email
        tvOtherBioProfile.text = user.bio
        tv_otherProfileName.text = user.username
        tvOtherFollowersNumber.text = "" + user.followerCount
        tvOtherFollowingNumber.text = "" + user.followingCount
        Glide.with(this).load(user.avatar).into(ivOtherAvatarProfile)

        if(user.following){
            btnFollowOtherProfile.text = "Following"
            btnFollowOtherProfile.setBackgroundColor(getColor(R.color.bleu_de_france))
        }
        else{
            btnFollowOtherProfile.text = "Follow"
        }

        userModel = user
    }

    override fun getCurrentUserPosts(posts: List<Post>) {
        userPosts = posts
        tvOtherPostsNumber.text = "" + posts.size

        val listImages = arrayListOf<String>()
        posts.forEach { x -> listImages.add(x.listMedia[0]) }

        val fragment = supportFragmentManager.findFragmentByTag("f" + 0) as GridMediaFragment
        fragment.updateData(listImages)
    }
}