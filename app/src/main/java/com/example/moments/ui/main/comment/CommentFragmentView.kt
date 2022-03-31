package com.example.moments.ui.main.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.moments.R

class CommentFragmentView : Fragment() {
    private var toolBar: Toolbar? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolBar = getView()?.findViewById(R.id.tbCommentHeader)
        toolBar?.title = "Comments"
        toolBar?.setNavigationOnClickListener {
            //TODO return newsfeed
        }
        onItemSelected()
    }

    private fun onItemSelected() {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sharePostBtn -> {
                    // Navigate to settings screen
                    true
                }
                else -> false
            }
        }
    }
}