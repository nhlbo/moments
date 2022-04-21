package com.example.moments.ui.main.settings.changePassword

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable

interface IChangePasswordView : IBaseView{
    fun onChangePasswordSuccess()
}

interface IChangePasswordInteractor : IBaseInteractor {
    fun doChangePassword(oldPassword: String, newPassword: String): Completable
}

interface IChangePasswordPresenter<V : IChangePasswordView, I : IChangePasswordInteractor> :
    IBasePresenter<V, I> {
    fun onSubmitChangePassword(oldPassword: String, newPassword: String, confirmNewPassword: String)
}