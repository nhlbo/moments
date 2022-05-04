package com.example.moments.ui.main.newsFeed

import com.example.moments.data.model.Post
import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Completable
import io.reactivex.Single

interface INewsFeedView : IBaseView {
    fun updatePost(listPost: List<RetrievedPost>)
}

interface INewsFeedInteractor : IBaseInteractor {
    fun doQueryFeedPost(): Single<List<RetrievedPost>>
    fun doLikePost(postId: String): Completable
    fun doUnlikePost(postId: String): Completable
    fun doBookmarkPost(postId: String): Completable
    fun doUnBookmarkPost(postId: String): Completable
}

interface INewsFeedPresenter<V : INewsFeedView, I : INewsFeedInteractor> : IBasePresenter<V, I> {
    fun onViewPrepared()
    fun onLikePost(postId: String)
    fun onUnlikePost(postId: String)
    fun onBookmarkPost(postId: String)
    fun onUnBookmarkPost(postId: String)
}