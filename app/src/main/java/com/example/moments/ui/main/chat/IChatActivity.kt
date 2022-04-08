package com.example.moments.ui.main.chat

import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable

interface IChatActivityView : IBaseView {
    fun fetchChat(users: List<User>)
    fun addMessage(message: Message)
}

interface IChatActivityPresenter<V : IChatActivityView, I : IChatActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformSendMessage(message: Message)
    fun getCurrentUserId(): String
}

interface IChatActivityInteractor : IBaseInteractor {
    fun doPerformSendMessage(message: Message): Completable
    fun getCurrentUserId(): String
}
