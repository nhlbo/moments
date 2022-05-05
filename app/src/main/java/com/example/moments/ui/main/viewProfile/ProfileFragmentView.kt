package com.example.moments.ui.main.viewProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.Moment
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener
import com.example.moments.ui.main.editProfile.EditProfileActivityView
import com.example.moments.ui.main.qrCode.QRCodeActivityView
import com.example.moments.ui.main.settings.SettingsActivityView
import com.example.moments.ui.main.viewFollowList.ViewFollowListActivityView
import com.example.moments.ui.main.viewPost.ViewPostActivityView
import com.example.moments.ui.vuforia.VuforiaActivityView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_view_profile.*
import javax.inject.Inject

class ProfileFragmentView : BaseFragment(), IProfileView {

    companion object {
        fun newInstance(): ProfileFragmentView {
            return ProfileFragmentView()
        }

        const val USER_KEY = "USER_KEY"
        const val REQUEST_EDIT_PROFILE = 1
    }

    @Inject
    internal lateinit var presenter: IProfilePresenter<IProfileView, IProfileInteractor>

    private var viewPager: ViewPager2? = null

    private var userModel: User? = null

    override fun setUp() {
        presenter.onViewPrepared()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_view_profile, container, false)
        setHasOptionsMenu(true)
        initMediaGrid(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)

        profileToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.btnScanFace -> {
                    val intent = Intent(activity, VuforiaActivityView::class.java)
                    startActivity(intent)
                }
                R.id.btnQRCode -> {
                    if (userModel != null) {
                        val intent = Intent(activity, QRCodeActivityView::class.java)
                        intent.putExtra(USER_KEY, userModel)
                        startActivity(intent)
                    }
                }
                R.id.profileSettingBtn -> {
                    val intent = Intent(activity, SettingsActivityView::class.java)
                    startActivity(intent)
                }
            }
            false
        }
        presenter.onViewPrepared()
        initLayout(view)

        btnEditProfile.setOnClickListener {
            if (userModel == null) return@setOnClickListener

            val intent = Intent(activity, EditProfileActivityView::class.java)
            intent.putExtra(USER_KEY, userModel)
            startActivityForResult(intent, REQUEST_EDIT_PROFILE)
        }
    }

    override fun getCurrentUserModel(user: User) {
        Glide.with(this).load(user.avatar).into(ivAvatarProfile)
        tvUsernameProfile.text = user.username
        tvHashtagProfile.text = user.email
        tvBioProfile.text = user.bio
        tvFollowersNumber.text = "${user.followerCount}"
        tvFollowingNumber.text = "${user.followingCount}"
        userModel = user
    }

    private lateinit var userPosts: List<Post>
    override fun getCurrentUserPosts(posts: List<Post>) {
        userPosts = posts
        tvPostsNumber.text = "${posts.size}"

        val capacity = Math.max(12, posts.size)
        val listImages = MutableList(capacity) { "" }
        for (i in posts.indices) {
            listImages[i] = posts[i].listMedia[0]
        }

        val fragment = childFragmentManager.findFragmentByTag("f" + 0) as GridMediaFragment
        fragment.updateData(listImages)


    }

    private lateinit var userMoments: List<Moment>
    override fun getCurrentUserMoments(moments: List<Moment>) {
        userMoments = moments
        val capacity = Math.max(12, moments.size)
        val listData = MutableList(capacity) { "" }
        for (i in moments.indices) {
            listData[i] = moments[i].media
        }
        val fragment = childFragmentManager.findFragmentByTag("f" + 1) as GridMediaFragment
        fragment.updateData(listData)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }


    private val onPostClickListener = object : IOnRecyclerViewItemTouchListener {
        override fun onItemClick(position: Int) {
            val intent = Intent(requireContext(), ViewPostActivityView::class.java)
            intent.putExtra("postId", userPosts[position].id)
            startActivity(intent)
        }
    }
    private val onVideoClickListener = object : IOnRecyclerViewItemTouchListener {
        override fun onItemClick(position: Int) {
            //View moment
        }
    }

    private fun initMediaGrid(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout_view_profile)
        viewPager = view.findViewById(R.id.vp2_view_profile)
        viewPager?.adapter =
            MediaGridViewPagerAdapter(this, onPostClickListener, onVideoClickListener)
        viewPager?.isUserInputEnabled = false
        viewPager?.offscreenPageLimit = 1
        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid)
                1 -> tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_video)
            }
        }.attach()
    }

    private fun initLayout(view: View) {
        val linearLayoutFollowers = view.findViewById<LinearLayout>(R.id.llFollowers)
        val linearLayoutFollowing = view.findViewById<LinearLayout>(R.id.llOtherFollowing)

        linearLayoutFollowers.setOnClickListener(View.OnClickListener {
            if (userModel == null) return@OnClickListener
            val intent = Intent(activity, ViewFollowListActivityView::class.java)
            intent.putExtra(USER_KEY, userModel)
            intent.putExtra("FollowViewType", "0")
            startActivity(intent)
        })
        linearLayoutFollowing.setOnClickListener(View.OnClickListener {
            if (userModel == null) return@OnClickListener
            val intent = Intent(activity, ViewFollowListActivityView::class.java)
            intent.putExtra(USER_KEY, userModel)
            intent.putExtra("FollowViewType", "1")
            startActivity(intent)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_PROFILE) {
            if (resultCode == EditProfileActivityView.DONE) {
                val username = data?.getStringExtra("USERNAME")
                val bio = data?.getStringExtra("BIO")
                val image = data?.extras!!["AVA"]
                tvUsernameProfile.text = username
                tvBioProfile.text = bio
                Glide.with(this).load(image).into(ivAvatarProfile)
            }
        }
    }
}