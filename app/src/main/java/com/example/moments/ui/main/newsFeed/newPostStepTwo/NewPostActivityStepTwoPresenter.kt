package com.example.moments.ui.main.newsFeed.newPostStepTwo

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewPostActivityStepTwoPresenter<V : INewPostStepTwoView, I : INewPostStepTwoInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), INewPostStepTwoPresenter<V, I> {
    override fun onCreatePost(caption: String, listMedia: List<ByteArray>) {
        interactor?.let {
            compositeDisposable.add(
                it.doCreatePost(caption, listMedia)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.backToFeedActivity()
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

}