package com.example.moments.ui.signUp

import com.example.moments.ui.base.BasePresenter
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
        TODO("Not yet implemented")
    }

    override fun onGotoLoginClicked() {
        getView()?.openLoginActivity()
    }
}