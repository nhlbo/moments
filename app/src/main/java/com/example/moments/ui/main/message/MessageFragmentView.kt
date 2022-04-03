package com.example.moments.ui.main.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.moments.R
import com.example.moments.ui.main.MainActivityView

class MessageFragmentView : Fragment() {
    private lateinit var toolbar: Toolbar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_message, container, false)
        toolbar = view.findViewById(R.id.tbMessageActivity)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
            (activity as MainActivityView).selectIndex(0)
        }
    }
}