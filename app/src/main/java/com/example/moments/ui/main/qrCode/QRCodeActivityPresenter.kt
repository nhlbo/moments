package com.example.moments.ui.main.qrCode

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class QRCodeActivityPresenter<V : IQRCodeActivityView, I : IQRCodeActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IQRCodeActivityPresenter<V, I> {

    override fun onPerformEditProfile(username: String, bio: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doPerformEditProfile(username, bio).compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
}