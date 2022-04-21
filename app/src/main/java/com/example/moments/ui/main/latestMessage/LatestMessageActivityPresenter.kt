package com.example.moments.ui.main.latestMessage

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LatestMessageActivityPresenter<V : ILatestMessageActivityView, I : ILatestMessageActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    ILatestMessageActivityPresenter<V, I> {

    override fun onListenToLatestMessage() {
        interactor?.let {
            compositeDisposable.add(
                it.doListenToLatestMessage()
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe({
                        getView()?.addLatestMessages(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

}