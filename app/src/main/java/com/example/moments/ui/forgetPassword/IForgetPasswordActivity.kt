package com.example.moments.ui.forgetPassword

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable

interface IForgetPasswordActivityView : IBaseView {
    fun backToLoginActivity()
    fun showValidationMessage(errorCode: Int)
}

interface IForgetPasswordActivityInteractor : IBaseInteractor {
    fun doRequestForgetPassword(email: String): Completable
}

interface IForgetPasswordActivityPresenter<V : IForgetPasswordActivityView, I : IForgetPasswordActivityInteractor> :
    IBasePresenter<V, I> {
    fun onForgetPasswordClicked(email: String)
}