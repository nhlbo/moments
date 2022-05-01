package com.example.moments.ui.main.viewPost

import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.example.moments.ui.main.newsFeed.INewsFeedView
import io.reactivex.Completable
import io.reactivex.Single

interface IViewPostView : IBaseView {
    fun updatePost(post: RetrievedPost)
}

interface IViewPostInteractor : IBaseInteractor {
    fun doQueryFeedPost(): Single<RetrievedPost>
    fun doLikePost(postId: String): Completable
    fun doUnlikePost(postId: String): Completable
}

interface IViewPostPresenter<V : IViewPostView, I : IViewPostInteractor> :
    IBasePresenter<V, I> {
    fun onViewPrepared()
    fun onLikePost()
    fun onUnlikePost()
}