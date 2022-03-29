package com.example.moments.ui.login

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.example.moments.util.AppConstants
import io.reactivex.Completable
import io.reactivex.Observable

interface ILoginActivityView : IBaseView {
    fun openSignUpActivity()
    fun openMainActivity()
    fun openForgotPasswordActivity()
    fun showValidationMessage(errorCode: Int)
}

interface ILoginActivityPresenter<V : ILoginActivityView, I : ILoginActivityInteractor> :
    IBasePresenter<V, I> {
    fun listenToAuthStateChange()
    fun onServerLoginClicked(email: String, password: String)
    fun onGoogleLoginClicked()
    fun onGoToSignUpClicked()
    fun onGoToForgotPasswordClicked()
}

interface ILoginActivityInteractor : IBaseInteractor {
    fun observeAuthStateChange(): Observable<Boolean>
    fun doServerLogin(email: String, password: String): Completable
    fun doGoogleLogin(): Observable<Any>
    fun updateUserLoginStatus(loggedInMode: AppConstants.LoggedInMode)
}