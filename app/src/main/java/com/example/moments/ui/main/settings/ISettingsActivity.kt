package com.example.moments.ui.main.settings

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface ISettingsActivityView : IBaseView {
//    fun addMessages(messages: List<Message>)
}

interface ISettingsActivityPresenter<V : ISettingsActivityView, I : ISettingsActivityInteractor> :
    IBasePresenter<V, I> {
//    fun onPerformSendMessage(message: Message)
//    fun getCurrentUserId(): String
//    fun onPerformListenToMessage()
}

interface ISettingsActivityInteractor : IBaseInteractor {
//    fun doPerformSendMessage(message: Message): Completable
//    fun getCurrentUserId(): String
//    fun doPerformListenToMessage(): Observable<List<Message>>
}
