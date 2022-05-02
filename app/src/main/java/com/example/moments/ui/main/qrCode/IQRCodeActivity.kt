package com.example.moments.ui.main.qrCode

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable

interface IQRCodeActivityView : IBaseView {
}

interface IQRCodeActivityPresenter<V : IQRCodeActivityView, I : IQRCodeActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformEditProfile(username: String, bio: String)
}

interface IQRCodeActivityInteractor : IBaseInteractor {
    fun doPerformEditProfile(username: String, bio: String): Completable
}
