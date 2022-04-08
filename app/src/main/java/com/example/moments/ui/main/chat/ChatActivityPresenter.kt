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
                        getView()?.addMessage(message)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

    override fun getCurrentUserId(): String = interactor?.getCurrentUserId().toString()
}