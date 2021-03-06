package com.example.moments.ui.main.newsFeed.newPostStepTwo

import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Single

interface INewPostStepTwoView : IBaseView {
    fun backToFeedActivity()
}

interface INewPostStepTwoInteractor : IBaseInteractor {
    fun doCreatePost(caption: String, listMedia: List<ByteArray>): Single<RetrievedPost>
    fun doCreateMoment(caption: String, media: ByteArray): Completable
}

interface INewPostStepTwoPresenter<V : INewPostStepTwoView, I : INewPostStepTwoInteractor> :
    IBasePresenter<V, I> {
    fun onCreatePost(caption: String, listMedia: List<ByteArray>)
    fun onCreateMoment(caption: String, media: ByteArray)
}