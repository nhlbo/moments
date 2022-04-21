package com.example.moments.ui.main.newsFeed

import com.example.moments.data.model.Post
import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Single

interface INewsFeedView : IBaseView {
    fun updatePost(listPost: List<DocumentSnapshot>)
}

interface INewsFeedInteractor : IBaseInteractor {
    fun doQueryFeedPost(): Single<List<RetrievedPost>>
}

interface INewsFeedPresenter<V : INewsFeedView, I : INewsFeedInteractor> : IBasePresenter<V, I> {
    fun onViewPrepared()
}