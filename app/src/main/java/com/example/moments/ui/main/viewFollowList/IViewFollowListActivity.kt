package com.example.moments.ui.main.viewFollowList

import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface IViewFollowListActivityView : IBaseView {
    fun addFollowerUsers(users: List<User>)
    fun addFollowingUsers(users: List<User>)
}

interface IViewFollowListActivityPresenter<V : IViewFollowListActivityView, I : IViewFollowListActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformQueryFollowerCurrentUser()
    fun onPerformQueryFollowingCurrentUser()
}

interface IViewFollowListActivityInteractor : IBaseInteractor {
    fun doPerformQueryFollowerCurrentUser(): Single<List<User>>
    fun doPerformQueryFollowingCurrentUser(): Single<List<User>>
}
