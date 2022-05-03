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
    override fun onViewPrepared(postId: String) {
        interactor?.let { it ->
            compositeDisposable.addAll(
                it.doQueryPostComment(postId)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updatePostComment(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    }),
                it.doQueryFeedPost(postId)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updatePost(it)
                    },{
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onUploadComment(postId: String, content: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doAddComment(postId, content)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updateComment(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onUploadReply(postId: String, commentId: String, content: String) {
        interactor?.let { it ->
            compositeDisposable.add(
                it.doAddReply(postId, commentId, content)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updateReply(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun onLikePost(postId: String) {
        TODO("Not yet implemented")
    }

    override fun onUnlikePost(postId: String) {
        TODO("Not yet implemented")
    }
}