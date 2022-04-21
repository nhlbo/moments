package com.example.moments.ui.main.latestMessage

import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface ILatestMessageActivityView : IBaseView {
    fun addLatestMessages(latestMessage: List<Pair<User, Message>>)
}

interface ILatestMessageActivityPresenter<V : ILatestMessageActivityView, I : ILatestMessageActivityInteractor> :
    IBasePresenter<V, I> {
    fun onQueryLatestMessage()
}

interface ILatestMessageActivityInteractor : IBaseInteractor {
    fun doQueryLatestMessage(): Single<List<Pair<User, Message>>>
}
