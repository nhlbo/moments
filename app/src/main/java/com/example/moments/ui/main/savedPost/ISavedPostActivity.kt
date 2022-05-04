package com.example.moments.ui.main.savedPost

import com.example.moments.data.model.Post
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface ISavedPostActivityView : IBaseView {
    fun getBookmarkPosts(posts:List<Post>)
}

interface ISavedPostActivityPresenter<V : ISavedPostActivityView, I : ISavedPostActivityInteractor> :
    IBasePresenter<V, I> {
    fun onViewPrepared()
}

interface ISavedPostActivityInteractor : IBaseInteractor {
    fun doPerformQueryBookmarkPost(): Single<List<Post>>
}
