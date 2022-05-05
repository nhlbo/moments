package com.example.moments.ui.main.viewProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moments.R
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener

class MediaGridViewPagerAdapter: FragmentStateAdapter {
    constructor(fragmentManager: FragmentManager,
                lifecycle: Lifecycle,
                postListener: IOnRecyclerViewItemTouchListener,
                videoListener: IOnRecyclerViewItemTouchListener
    ) : super(fragmentManager, lifecycle)
    {
        this.postListener = postListener
        this.videoListener = videoListener
    }

    constructor(fragment: Fragment,
                postListener: IOnRecyclerViewItemTouchListener,
                videoListener: IOnRecyclerViewItemTouchListener
    ) : super(fragment)
    {
        this.postListener = postListener
        this.videoListener = videoListener
    }

    private var postListener: IOnRecyclerViewItemTouchListener
    private var videoListener: IOnRecyclerViewItemTouchListener
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> GridMediaFragment(postListener)
            else -> GridMediaFragment(videoListener)
        }
    }
}

class GridMediaFragment(private val listener: IOnRecyclerViewItemTouchListener) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.component_grid_media, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }
    private var imageList = arrayListOf<String>()
    private var recyclerView: RecyclerView? = null
    private fun initRecyclerView(view: View?) {
        recyclerView= view?.findViewById(R.id.rcMediaGrid)!!
        recyclerView?.setHasFixedSize(true);
        recyclerView?.addItemDecoration(
            DividerItemDecoration(context,
                DividerItemDecoration.HORIZONTAL)
        )
        recyclerView?.addItemDecoration(
            DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
        recyclerView?.layoutManager = GridLayoutManager(activity,3)
        val adapter = context?.let { ImagesAdapter(it,imageList, listener) }
        recyclerView?.adapter =adapter
    }

    fun updateData(data: List<String>){
        imageList = ArrayList(data)
        recyclerView?.adapter = ImagesAdapter(requireContext(), imageList, listener)
    }
}