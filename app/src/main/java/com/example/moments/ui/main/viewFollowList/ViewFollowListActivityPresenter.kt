package com.example.moments.ui.main.viewFollowList

import android.util.Log
import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ViewFollowListActivityPresenter<V : IViewFollowListActivityView, I : IViewFollowListActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IViewFollowListActivityPresenter<V, I> {

    override fun onPerformQueryFollowerCurrentUser() {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doPerformQueryFollowerCurrentUser()
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.addFollowingUsers(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onPerformQueryFollowingCurrentUser() {
         interactor?.let { it ->
            compositeDisposable.add(
                it.doPerformQueryFollowerCurrentUser()
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.addFollowingUsers(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }


}