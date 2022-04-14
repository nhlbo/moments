package com.example.moments.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeFragmentView : Fragment() {
    private lateinit var parentViewPager: ViewPager2
    private lateinit var controller: NavController

    private var mOnButtonClickListener: OnButtonClickListener? = null

    internal interface OnButtonClickListener {
        fun onButtonClicked(view: View?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnButtonClickListener = context as OnButtonClickListener
        } catch (e: ClassCastException) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val result: View = inflater.inflate(R.layout.activity_home, container, false)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentViewPager) as NavHostFragment
        controller = navHostFragment.navController
        parentViewPager = activity?.findViewById(R.id.fragmentContainerView)!!

        bottomNavigationView.setupWithNavController(controller)
        controller.addOnDestinationChangedListener { _, destination, _ ->
            parentViewPager.isUserInputEnabled = destination.id == R.id.navFeedMenu
        }
        //initNavigationBar()
        return result
    }

    private fun initNavigationBar() {
        bottomNavigationView.setOnItemSelectedListener {
            run {
                when (it.itemId) {
                    R.id.navFeedMenu -> {
                        parentViewPager.isUserInputEnabled = true
                        true
                    }
                    else -> {
                        parentViewPager.isUserInputEnabled = false
                        false
                    }
                }
            }
        }
    }

    fun getController(): NavController = controller


//    override fun replaceFragment(fragment: Fragment?) {
//        val fragmentManager: FragmentManager = childFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.navigateFragmentsContainer, fragment!!, fragment.toString())
//        fragmentTransaction.addToBackStack(fragment.toString())
//        fragmentTransaction.commit()
//    }

}