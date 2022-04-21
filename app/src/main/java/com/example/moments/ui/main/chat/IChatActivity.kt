package com.example.moments.ui.main.chat

import com.example.moments.data.model.Message
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Observable

interface IChatActivityView : IBaseView {
    fun addMessages(messages: List<Message>)
}

interface IChatActivityPresenter<V : IChatActivityView, I : IChatActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformSendMessage(message: Message)
    fun getCurrentUserId(): String
    fun onPerformListenToMessage()
}

interface IChatActivityInteractor : IBaseInteractor {
    fun doPerformSendMessage(message: Message): Completable
    fun getCurrentUserId(): String
    fun doPerformListenToMessage(): Observable<List<Message>>
}
