package com.example.moments.ui.main.newMessage

import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface INewMessageActivityView : IBaseView {
    fun addUsers(users: List<User>)
}

interface INewMessageActivityPresenter<V : INewMessageActivityView, I : INewMessageActivityInteractor> :
    IBasePresenter<V, I> {
    fun onQueryFollowingUser()
}

interface INewMessageActivityInteractor : IBaseInteractor {
    fun doQueryFollowingUser(): Single<List<User>>
}
