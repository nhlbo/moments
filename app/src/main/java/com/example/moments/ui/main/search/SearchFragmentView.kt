package com.example.moments.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moments.R

class SearchFragmentView : Fragment(R.layout.activity_search) {
    companion object {
        fun newInstance(): SearchFragmentView {
            return SearchFragmentView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_search, container, false)
    }

    override fun toString(): String = "searchFragment"
}