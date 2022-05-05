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
            compositeDisposable.addAll(
                it.doQueryFeedPost().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updatePost(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    }),
                it.doGetCurrentUserModel().compose(
                    schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.getCurrentUser(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onLikePost(postId: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doLikePost(postId).compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
//                        getView()?.updateLikePost(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onUnlikePost(postId: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doUnlikePost(postId).compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
//                        getView()?.updateUnLikePost(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
    override fun onBookmarkPost(postId: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doBookmarkPost(postId).compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
//                        getView()?.updateLikePost(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onUnBookmarkPost(postId: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doUnBookmarkPost(postId).compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
//                        getView()?.updateUnLikePost(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onGetCurrentUserModel() {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doGetCurrentUserModel().compose(
                    schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.getCurrentUser(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
}