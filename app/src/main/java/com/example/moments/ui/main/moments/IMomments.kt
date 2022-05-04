package com.example.moments.ui.main.moments

import com.example.moments.data.model.RetrievedMoment
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Single

interface IMommentsInteractor : IBaseInteractor {
    fun doLikeMoment(momentId: String): Completable
    fun doUnlikeMoment(momentId: String): Completable
    fun doQueryFeedMoment(): Single<List<RetrievedMoment>>
}

interface IMommentsView : IBaseView

interface IMommentsPresenter<V : IMommentsView, I : IMommentsInteractor> : IBasePresenter<V, I> {
    fun onLikeMoment(momentId: String)
    fun onUnlikeMoment(momentId: String)
    fun onViewPrepared()
}