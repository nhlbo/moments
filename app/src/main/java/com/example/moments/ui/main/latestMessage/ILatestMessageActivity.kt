package com.example.moments.ui.main.latestMessage

import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface ILatestMessageActivityView : IBaseView {
    fun addUsers(users: List<User>)
}

interface ILatestMessageActivityPresenter<V : ILatestMessageActivityView, I : ILatestMessageActivityInteractor> :
    IBasePresenter<V, I> {
    fun onQueryFollowingUser()
}

interface ILatestMessageActivityInteractor : IBaseInteractor {
    fun doQueryFollowingUser(): Single<List<User>>
}
