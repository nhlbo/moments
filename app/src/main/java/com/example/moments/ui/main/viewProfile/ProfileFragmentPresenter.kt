package com.example.moments.ui.main.viewProfile

import android.util.Log
import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileFragmentPresenter<V : IProfileView, I : IProfileInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), IProfilePresenter<V, I> {
    override fun onViewPrepared() {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doQueryCurrentUserPost().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
//                        Log.d("debug", it.toString())
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
}