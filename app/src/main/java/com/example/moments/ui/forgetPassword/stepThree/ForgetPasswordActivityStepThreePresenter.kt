package com.example.moments.ui.forgetPassword.stepThree

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForgetPasswordActivityStepThreePresenter<V : IForgetPasswordActivityStepThreeView, I : IForgetPasswordActivityStepThreeInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable

),
    IForgetPasswordActivityStepThreePresenter<V, I> {
    override fun onForgetPasswordStepThreeContinueClicked(
        newPassword: String,
        confirmNewPassword: String
    ) {
        // validate password
        getView()?.openLoginActivity()
    }
}