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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.editProfile.EditProfileActivityView
import com.example.moments.ui.main.settings.SettingsActivityView
import com.example.moments.ui.forgetPassword.ForgetPasswordActivityView
import com.example.moments.ui.main.viewFollowList.ViewFollowTabActivityView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_view_profile.*
import javax.inject.Inject

class ProfileFragmentView : BaseFragment(), IProfileView {

    companion object {
        fun newInstance(): ProfileFragmentView {
            return ProfileFragmentView()
        }
    }

    @Inject
    internal lateinit var presenter: IProfilePresenter<IProfileView, IProfileInteractor>

    private var toolBar: Toolbar? = null

    private var viewPager: ViewPager2? = null

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
            if (item.itemId == R.id.profileSettingBtn) {
                val intent = Intent(activity, SettingsActivityView::class.java)
                startActivity(intent)
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
        btnEditProfile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivityView::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
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
//        toolBar = view?.findViewById(R.id.profile_header_toolbar)
//        toolBar?.inflateMenu(R.menu.header_profile)
//        toolBar?.title = "Your profile"
//        onItemSelected()
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