package com.example.moments.ui.main.editProfile

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable

interface IEditProfileActivityView : IBaseView {
}

interface IEditProfileActivityPresenter<V : IEditProfileActivityView, I : IEditProfileActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformEditProfile(username: String, bio: String)
}

interface IEditProfileActivityInteractor : IBaseInteractor {
    fun doPerformEditProfile(username: String, bio: String): Completable
}
