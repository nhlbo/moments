package com.example.moments.ui.main.viewProfile

import com.example.moments.data.model.Post
import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface IProfileView : IBaseView

interface IProfileInteractor : IBaseInteractor {
    fun doQueryCurrentUserPost(): Single<List<Post>>
}

interface IProfilePresenter<V : IProfileView, I : IProfileInteractor> : IBasePresenter<V, I> {
    fun onViewPrepared()
}