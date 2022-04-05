package com.example.moments.ui.main.new_message

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Single

interface INewMessageActivityView : IBaseView {
    fun addUsers(users: List<DocumentSnapshot>)
}

interface INewMessageActivityPresenter<V : INewMessageActivityView, I : INewMessageActivityInteractor> :
    IBasePresenter<V, I> {
    fun onQueryFollowingUser()
}

interface INewMessageActivityInteractor : IBaseInteractor {
    fun doQueryFollowingUser(): Single<List<DocumentSnapshot>>
}
