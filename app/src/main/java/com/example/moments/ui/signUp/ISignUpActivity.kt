package com.example.moments.ui.signUp

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable

interface ISignUpActivityView : IBaseView {
    fun openLoginActivity()
    fun showValidationMessage(errorCode: Int)
}

interface ISignUpActivityInteractor : IBaseInteractor {
    fun doSignUp(email: String, username: String, password: String): Completable
}

interface ISignUpActivityPresenter<V : ISignUpActivityView, I : ISignUpActivityInteractor> :
    IBasePresenter<V, I> {
    fun onSignUpClicked(email: String, username: String, password: String, confirmPassword: String)
    fun onGotoLoginClicked()
}