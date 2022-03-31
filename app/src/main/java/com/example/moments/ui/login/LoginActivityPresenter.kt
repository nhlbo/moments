package com.example.moments.ui.login

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.AppConstants
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginActivityPresenter<V : ILoginActivityView, I : ILoginActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    ILoginActivityPresenter<V, I> {

    override fun onServerLoginClicked(email: String, password: String) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
            password.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_PASSWORD_ERROR)
            else -> {
//                getView()?.showProgress()
                interactor?.let {
                    compositeDisposable.add(
                        it.doServerLogin(email, password)
                            .compose(schedulerProvider.ioToMainCompletableScheduler())
                            .subscribe({
                                getView()?.openMainActivity()
                            }, {
                                getView()?.showCustomToastMessage(it.localizedMessage)
                            })
                    )
                }
            }
        }
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