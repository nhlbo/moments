package com.example.moments.ui.main.viewProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.moments.R

class ProfileFragmentView : Fragment(R.layout.activity_view_profile) {
    private var toolBar: Toolbar? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_view_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBar = getView()?.findViewById(R.id.profile_header_toolbar)
        toolBar?.inflateMenu(R.menu.header_profile)
        toolBar?.title = "Your profile"
        onItemSelected()
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