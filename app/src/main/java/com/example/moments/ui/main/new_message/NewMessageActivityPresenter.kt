package com.example.moments.ui.main.new_message

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.AppConstants
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewMessageActivityPresenter<V : INewMessageActivityView, I : INewMessageActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    INewMessageActivityPresenter<V, I> {

    //    override fun onServerNewMessageClicked(email: String, password: String) {
//        when {
//            email.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_EMAIL_ERROR)
//            password.isEmpty() -> getView()?.showValidationMessage(AppConstants.EMPTY_PASSWORD_ERROR)
//            else -> {
//                interactor?.let {
//                    compositeDisposable.add(
//                        it.doServerNewMessage(email, password)
//                            .compose(schedulerProvider.ioToMainCompletableScheduler())
//                            .subscribe({
//                                getView()?.openMainActivity()
//                            }, {
//                                getView()?.showCustomToastMessage(it.localizedMessage)
//                            })
//                    )
//                }
//            }
//        }
//    }
    override fun onQueryFollowingUser() {
        interactor?.let {
            compositeDisposable.add(
                it.doQueryFollowingUser()
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.addUsers(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
}