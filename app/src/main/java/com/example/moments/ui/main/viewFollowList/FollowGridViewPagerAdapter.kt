package com.example.moments.ui.main.viewFollowList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moments.R

class FollowGridViewPagerAdapter(
    fragment: FragmentActivity,
    val dataFollowersList: List<Followers>,
    val dataFollowingList: List<Following>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return LinearFollowerFragment(dataFollowersList)
            else -> {return LinearFollowingFragment(dataFollowingList)}
        }

    }

}

class LinearFollowerFragment(val dataList: List<Followers>) : Fragment() {
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

    private fun initRecyclerView(view: View?) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rcFollowView)
        recyclerView?.setHasFixedSize(true);
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        var adapter = context?.let { FollowersAdapter(it, dataList) }
        recyclerView?.adapter = adapter
    }
}

class LinearFollowingFragment(val dataList: List<Following>) : Fragment() {
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

    private fun initRecyclerView(view: View?) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rcFollowView)
        recyclerView?.setHasFixedSize(true);
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        var adapter = context?.let { FollowingAdapter(it, dataList) }
        recyclerView?.adapter = adapter
    }
}