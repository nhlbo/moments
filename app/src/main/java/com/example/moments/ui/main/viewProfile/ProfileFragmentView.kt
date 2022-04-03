package com.example.moments.ui.main.viewProfile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R

class ProfileFragmentView : Fragment(R.layout.activity_view_profile) {
    private var toolBar: Toolbar? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.activity_view_profile, container, false)
        initRecyclerView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar = getView()?.findViewById(R.id.profile_header_toolbar)
        toolBar?.inflateMenu(R.menu.header_profile)
        toolBar?.title = "Your profile"
        onItemSelected()
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