package com.example.moments.ui.main.newsFeed.newPost

import android.util.Log
import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewPostActivityPresenter<V : INewPostActivityView, I : INewPostActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), INewPostActivityPresenter<V, I> {
    override fun onUploadMedia(listMedia: List<ByteArray>) {
        interactor?.let{
            compositeDisposable.add(
                it.doUploadMedia(listMedia).compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                               Log.d("gg", it.map{it.toString()}.toString())
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
}