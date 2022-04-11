package com.example.moments.ui.main.settings

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingsActivityPresenter<V : ISettingsActivityView, I : ISettingsActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    ISettingsActivityPresenter<V, I> {

    override fun onPerformLogOut() {
        interactor?.doPerformLogOut()
    }
}