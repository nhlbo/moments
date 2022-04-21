package com.example.moments.ui.signUp

import android.util.Log
import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.AppConstants
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SignUpActivityPresenter<V : ISignUpActivityView, I : ISignUpActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable

) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    ISignUpActivityPresenter<V, I> {
    override fun onSignUpClicked(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ) {
        when {
            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
            username.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_USERNAME_ERROR)
            password.isEmpty() || confirmPassword.isEmpty() -> getView()?.showValidationMessage(
                AppConstants.EMPTY_PASSWORD_ERROR
            )
            password != confirmPassword -> getView()?.showValidationMessage(AppConstants.CONFIRM_PASSWORD_NOT_MATCH_ERROR)
            else -> {
                interactor?.let {
                    compositeDisposable.add(
                        it.doSignUp(email, username, password)
                            .compose(schedulerProvider.ioToMainCompletableScheduler())
                            .subscribe({
                                getView()?.showCustomToastMessage("Register account success!") // too lazy to localize message
                                getView()?.openLoginActivity()
                            }, {
                                getView()?.showCustomToastMessage(it.localizedMessage)
                            })
                    )
                }
            }
        }
    }

    override fun onGotoLoginClicked() {
        getView()?.openLoginActivity()
    }
}