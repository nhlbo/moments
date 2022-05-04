package com.example.moments.ui.main.moments

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MommentsFragmentPresenter<V : IMommentsView, I : IMommentsInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), IMommentsPresenter<V, I> {
    override fun onLikeMoment(momentId: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doLikeMoment(momentId).compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
                        //
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onUnlikeMoment(momentId: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doUnlikeMoment(momentId)
                    .compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
                        //
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onViewPrepared() {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doQueryFeedMoment()
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
//                        getView()?.getListMoment(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

}