package com.example.moments.ui.main.moments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moments.R
import com.example.moments.ui.main.viewProfile.ProfileFragmentView

class MomentsFragmentView : Fragment() {
    companion object {
        fun newInstance(): MomentsFragmentView {
            return MomentsFragmentView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_moments, container, false)
    }

    override fun toString(): String = "momentFragment"
}