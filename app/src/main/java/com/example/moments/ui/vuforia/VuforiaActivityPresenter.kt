package com.example.moments.ui.vuforia

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class VuforiaActivityPresenter<V : IVuforiaActivityView, I : IVuforiaActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IVuforiaActivityPresenter<V, I> {

    override fun onPerformQueryUserByVuforiaId(vuforiaId: String) {
        interactor?.let {
            compositeDisposable.add(
                it.doPerformQueryUserByVuforiaId(vuforiaId)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.getUserByVuforiaId(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

}