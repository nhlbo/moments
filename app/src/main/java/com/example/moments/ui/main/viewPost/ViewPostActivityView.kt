package com.example.moments.ui.main.viewPost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moments.R
import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.BaseActivity

class ViewPostActivityView : BaseActivity(), IViewPostView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun updatePost(post: RetrievedPost) {
        TODO("Not yet implemented")
    }
}