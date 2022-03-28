package com.example.moments.ui.login

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginActivityPresenter<V : ILoginActivityView, I : ILoginActivityInteractor> @Inject internal constructor(
    interactor: I
) : BasePresenter<V, I>(
    interactor = interactor
),
    ILoginActivityPresenter<V, I> {

    override fun onServerLoginClicked(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun onGoogleLoginClicked() {
        TODO("Not yet implemented")
    }

    override fun onGoToSignUpClicked() {
        getView()?.openSignUpActivity()
    }

    override fun onGoToForgotPasswordClicked() {
        getView()?.openForgotPasswordActivity()
    }
}