package com.example.moments.ui.main.notification

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NotificationFragmentPresenter<V: INotificationView, I: INotificationInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), INotificationPresenter<V, I> {
    override fun onViewPrepared() {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doQueryNotification().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updateList(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

}