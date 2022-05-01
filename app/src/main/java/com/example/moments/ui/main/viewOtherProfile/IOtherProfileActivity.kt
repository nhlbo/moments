package com.example.moments.ui.main.viewOtherProfile

import com.example.moments.data.model.Post
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Single

interface IOtherProfileActivityView : IBaseView {
    fun getCurrentUserModel(user: User)
    fun getCurrentUserPosts(posts:List<Post>)
}

interface IOtherProfileActivityPresenter<V : IOtherProfileActivityView, I : IOtherProfileActivityInteractor> :
    IBasePresenter<V, I> {
    fun onViewPrepared(userId: String)
}

interface IOtherProfileActivityInteractor : IBaseInteractor {
    fun doQueryUserPostByUserId(userId: String): Single<List<Post>>
    fun doQueryUserById(userId: String): Single<User>
}