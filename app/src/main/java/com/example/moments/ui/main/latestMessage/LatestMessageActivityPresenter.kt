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