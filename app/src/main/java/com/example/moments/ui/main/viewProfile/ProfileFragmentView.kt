package com.example.moments.ui.main.viewProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.editProfile.EditProfileActivityView
import com.example.moments.ui.main.settings.SettingsActivityView
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

    private fun initRecyclerView(view: View?) {
        val imageList = arrayListOf<String>()
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")

        val recyclerView = view?.findViewById<RecyclerView>(R.id.rcMediaGrid)
        recyclerView?.layoutManager = GridLayoutManager(activity, 3)
        var adapter = context?.let { ImagesAdapter(it, imageList) }
        recyclerView?.adapter = adapter
    }
}