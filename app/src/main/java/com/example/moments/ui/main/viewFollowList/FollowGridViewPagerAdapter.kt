package com.example.moments.ui.main.viewFollowList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moments.R

class FollowGridViewPagerAdapter(
    fragment: FragmentActivity,
    private val dataFollowersList: List<Followers>,
    private val dataFollowingList: List<Following>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LinearFollowerFragment(dataFollowersList)
            else -> {
                LinearFollowingFragment(dataFollowingList)
            }
        }
    }
}

class LinearFollowerFragment(private var dataList: List<Followers>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.follow_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private lateinit var recyclerView: RecyclerView
    private fun initRecyclerView(view: View?) {
        recyclerView = view?.findViewById(R.id.rcFollowView)!!
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { FollowersAdapter(it, dataList) }
        recyclerView.adapter = adapter
    }

    fun updateList(input: List<Followers>){
        dataList = input
        recyclerView.adapter = FollowersAdapter(requireContext(), dataList)
    }
}

class LinearFollowingFragment(private var dataList: List<Following>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.follow_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private lateinit var recyclerView: RecyclerView
    private fun initRecyclerView(view: View?) {
        recyclerView = view?.findViewById(R.id.rcFollowView)!!
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { FollowingAdapter(it, dataList) }
        recyclerView.adapter = adapter
    }
    fun updateList(input: List<Following>){
        dataList = input
        recyclerView.adapter = FollowingAdapter(requireContext(), dataList)
    }
}