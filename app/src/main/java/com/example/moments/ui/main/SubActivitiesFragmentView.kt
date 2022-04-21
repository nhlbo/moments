package com.example.moments.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moments.R

class SubActivitiesFragmentView: Fragment() {
    private lateinit var controller: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val result = inflater.inflate(R.layout.activity_others_fragment, container, false)

        val navHost = childFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        controller = navHost.navController
        return result
    }
}