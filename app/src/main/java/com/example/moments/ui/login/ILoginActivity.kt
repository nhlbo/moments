package com.example.moments.ui.login

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.example.moments.util.AppConstants
import io.reactivex.Observable

interface ILoginActivityView : IBaseView {
    fun openSignUpActivity()
    fun openFeedActivity()
    fun showValidationMessage(errorCode: Int)
}

interface ILoginActivityPresenter<V : ILoginActivityView, I : ILoginActivityInteractor> :
    IBasePresenter<V, I> {
    fun onServerLoginClicked(email: String, password: String)
    fun onGoogleLoginClicked()
    fun onGoToSignUpClicked()
}

interface ILoginActivityInteractor : IBaseInteractor {
    fun doServerLogin(email: String, password: String): Observable<Any>
    fun doGoogleLogin(): Observable<Any>
    fun updateUserInSharedPref(loginResponse: Any, loggedInMode: AppConstants.LoggedInMode)
}