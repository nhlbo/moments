package com.example.moments.ui.main.newsFeed.newPost

import android.net.Uri
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface INewPostActivityView : IBaseView {
    fun openCreatePostActivity()
}

interface INewPostActivityInteractor : IBaseInteractor {
    fun doUploadMedia(listMedia: List<ByteArray>): Single<List<Uri>>
}

interface INewPostActivityPresenter<V : INewPostActivityView, I : INewPostActivityInteractor> :
    IBasePresenter<V, I> {
    fun onUploadMedia(listMedia: List<ByteArray>)
}