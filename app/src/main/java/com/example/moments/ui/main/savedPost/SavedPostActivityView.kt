package com.example.moments.ui.main.savedPost

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.example.moments.data.model.Post
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.viewPost.ViewPostActivityView
import kotlinx.android.synthetic.main.activity_saved_post.*
import javax.inject.Inject

class SavedPostActivityView : BaseActivity(), ISavedPostActivityView {


    @Inject
    internal lateinit var presenter: ISavedPostActivityPresenter<ISavedPostActivityView, ISavedPostActivityInteractor>

    private var postsList = arrayListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_post)

        presenter.onAttach(this)
        presenter.onViewPrepared()

        tbSavedPostActivity.setNavigationOnClickListener { finish() }

    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun getBookmarkPosts(posts: List<Post>) {

        for(post in posts){
                 postsList.add(post)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {

        val recyclerView = findViewById<RecyclerView>(R.id.rcSavedPostActivity)
        recyclerView?.layoutManager = GridLayoutManager(this, 3)
        val adapter = this?.let { SavedPostAdapter(it, postsList, null) }
        recyclerView?.adapter = adapter

        adapter.onItemClick = { it ->
            val intent = Intent(this, ViewPostActivityView::class.java)
            intent.putExtra("type","1")
            intent.putExtra("postId", it.id)
            startActivity(intent)
        }
    }
}