package com.example.moments.ui.main.newsFeed

import android.util.Log
import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsFeedFragmentPresenter<V : INewsFeedView, I : INewsFeedInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), INewsFeedPresenter<V, I> {
    override fun onViewPrepared() {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doQueryFeedPost().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                               Log.d("debug", it.toString())
                    }, {
                            getView()?.showCustomToastMessage(it.localizedMessage)
                    })

            )
        }
    }
}