package com.example.moments.ui.start

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Completable
import io.reactivex.Single

interface IStartActivityView : IBaseView {
    fun openMainActivity()
    fun openLoginActivity()
}

interface IStartActivityInteractor : IBaseInteractor {
    fun test(): Single<DocumentSnapshot>
}

interface IStartActivityPresenter<V : IStartActivityView, I : IStartActivityInteractor> :
    IBasePresenter<V, I> {
    fun test()
}

