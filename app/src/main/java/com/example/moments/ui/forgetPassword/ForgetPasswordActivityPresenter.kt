package com.example.moments.ui.forgetPassword

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForgetPasswordActivityPresenter<V : IForgetPasswordActivityView, I : IForgetPasswordActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IForgetPasswordActivityPresenter<V, I> {
    override fun onForgetPasswordClicked(email: String) {
        interactor?.let {
            compositeDisposable.add(
                it.doRequestForgetPassword(email)
                    .compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
                        getView()?.showCustomToastMessage("Please check your email!")
                        getView()?.backToLoginActivity()
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }

    }


}