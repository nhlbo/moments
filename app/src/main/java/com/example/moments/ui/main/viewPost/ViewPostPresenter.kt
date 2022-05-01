package com.example.moments.ui.main.viewPost

import com.example.moments.ui.base.BasePresenter
import com.example.moments.ui.main.newsFeed.INewsFeedInteractor
import com.example.moments.ui.main.newsFeed.INewsFeedPresenter
import com.example.moments.ui.main.newsFeed.INewsFeedView
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ViewPostPresenter<V : IViewPostView, I : IViewPostInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), IViewPostPresenter<V, I> {
    override fun onViewPrepared() {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doQueryFeedPost().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updatePost(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onLikePost() {
        TODO("Not yet implemented")
    }

    override fun onUnlikePost() {
        TODO("Not yet implemented")
    }
}