package com.example.moments.ui.start

import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Single

interface IStartActivityView : IBaseView {
    fun openMainActivity()
    fun openLoginActivity()
}

interface IStartActivityInteractor : IBaseInteractor

interface IStartActivityPresenter<V : IStartActivityView, I : IStartActivityInteractor> :
    IBasePresenter<V, I>

