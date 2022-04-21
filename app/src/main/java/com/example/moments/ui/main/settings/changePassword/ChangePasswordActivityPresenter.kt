package com.example.moments.ui.main.settings.changePassword

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ChangePasswordActivityPresenter<V : IChangePasswordView, I : IChangePasswordInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), IChangePasswordPresenter<V, I> {
    override fun onSubmitChangePassword(
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ) {
        when {
            oldPassword.isEmpty() -> getView()?.showCustomToastMessage("")
            newPassword.isEmpty() -> getView()?.showCustomToastMessage("")
            confirmNewPassword.isEmpty() -> getView()?.showCustomToastMessage("")
            newPassword != confirmNewPassword -> getView()?.showCustomToastMessage("New password and confirm password does not match!")
            else -> {
                interactor?.let {
                    compositeDisposable.add(
                        it.doChangePassword(oldPassword, newPassword)
                            .compose(schedulerProvider.ioToMainCompletableScheduler())
                            .subscribe({
                                getView()?.showCustomToastMessage("Success!")
                                getView()?.onChangePasswordSuccess()
                            }, {
                                getView()?.showCustomToastMessage(it.localizedMessage)
                            })
                    )
                }
            }
        }
    }
}