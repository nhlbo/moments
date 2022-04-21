package com.example.moments.ui.main.chat

import com.example.moments.data.model.Message
import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ChatActivityPresenter<V : IChatActivityView, I : IChatActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IChatActivityPresenter<V, I> {

    override fun onPerformSendMessage(message: Message) {
        interactor?.let {
            compositeDisposable.add(
                it.doPerformSendMessage(message)
                    .compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe({
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun getCurrentUserId(): String = interactor?.getCurrentUserId().toString()

    override fun onPerformListenToMessage(userId: String) {
        interactor?.let {
            compositeDisposable.add(
                it.doPerformListenToMessage(userId)
                    .compose(schedulerProvider.ioToMainObservableScheduler())
                    .subscribe({
                        getView()?.addMessages(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
}