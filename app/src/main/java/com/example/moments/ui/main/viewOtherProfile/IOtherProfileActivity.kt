package com.example.moments.ui.main.viewOtherProfile

import com.example.moments.data.model.Moment
import com.example.moments.data.model.OtherUser
import com.example.moments.data.model.Post
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Single

interface IOtherProfileActivityView : IBaseView {
    fun getCurrentUserModel(user: OtherUser)
    fun getCurrentUserPosts(posts:List<Post>)
}

interface IOtherProfileActivityPresenter<V : IOtherProfileActivityView, I : IOtherProfileActivityInteractor> :
    IBasePresenter<V, I> {
    fun onViewPrepared(userId: String)
    fun isMyself(userId: String): Boolean
    fun onUnfollow(userId: String)
    fun onFollow(userId: String)
}

interface IOtherProfileActivityInteractor : IBaseInteractor {
    fun doQueryUserPostByUserId(userId: String): Single<List<Post>>
    fun doQueryUserById(userId: String): Single<OtherUser>
    fun doQueryUserMoment(userId: String): Single<List<Moment>>
    fun doGetCurrentUserId(): String
    fun doFollowingUser(userId: String):Completable
    fun doUnfollowingUser(userId: String):Completable
}