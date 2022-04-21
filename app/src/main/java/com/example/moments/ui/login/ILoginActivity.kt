package com.example.moments.ui.login

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.google.firebase.auth.AuthCredential
import io.reactivex.Completable

interface ILoginActivityView : IBaseView {
    fun openSignUpActivity()
    fun openMainActivity()
    fun openForgotPasswordActivity()
    fun openGoogleLoginActivity()
    fun showValidationMessage(errorCode: Int)
}

interface ILoginActivityPresenter<V : ILoginActivityView, I : ILoginActivityInteractor> :
    IBasePresenter<V, I> {
    fun onServerLoginClicked(email: String, password: String)
    fun onGoogleLoginClicked()
    fun onGoogleLoginProcess(credential: AuthCredential)
    fun onGoToSignUpClicked()
    fun onGoToForgotPasswordClicked()
}

interface ILoginActivityInteractor : IBaseInteractor {
    fun doServerLogin(email: String, password: String): Completable
    fun doGoogleLogin(credential: AuthCredential): Completable
}