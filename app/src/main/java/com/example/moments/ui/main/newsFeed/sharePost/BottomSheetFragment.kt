package com.example.moments.ui.main.newsFeed.sharePost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.example.moments.data.model.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment(private val userList: ArrayList<User>) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)
        initRecyclerView(view)
        return view
    }

    private var recyclerView: RecyclerView? = null

    private fun initRecyclerView(view: View?) {

        recyclerView = view?.findViewById(R.id.rcBottomSheet)!!

        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { ShareUserAdapter(it, userList, null) }
        recyclerView?.adapter = adapter
    }
}
