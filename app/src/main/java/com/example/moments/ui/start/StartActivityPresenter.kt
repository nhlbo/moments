package com.example.moments.ui.start

import android.util.Log
import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class StartActivityPresenter<V : IStartActivityView, I : IStartActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable

) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IStartActivityPresenter<V, I> {

    override fun onAttach(view: V?) {
        super.onAttach(view)
//        test()
        decideActivityToOpen()
    }

    private fun decideActivityToOpen() = getView()?.let {
        if (isUserLoggedIn()) {
            it.openMainActivity()
        } else {
            it.openLoginActivity()
        }
    }

    private fun isUserLoggedIn(): Boolean = interactor!!.isUserLoggedIn()
}