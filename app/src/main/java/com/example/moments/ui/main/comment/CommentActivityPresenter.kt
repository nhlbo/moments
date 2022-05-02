package com.example.moments.ui.main.comment

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CommentActivityPresenter<V : ICommentActivityView, I : ICommentActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), ICommentActivityPresenter<V, I> {
    override fun onPreparedView(postId: String) {
        interactor?.let { it ->
            compositeDisposable.addAll(
                it.doQueryPostComment(postId)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.updatePostComment(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    }),
                it.doQueryPost(postId)
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

}