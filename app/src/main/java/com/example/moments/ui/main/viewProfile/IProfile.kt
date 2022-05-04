package com.example.moments.ui.main.viewProfile

import android.provider.MediaStore
import com.example.moments.data.model.Moment
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Single

interface IProfileView : IBaseView {
    fun getCurrentUserModel(user: User)
    fun getCurrentUserPosts(posts:List<Post>)
}

interface IProfileInteractor : IBaseInteractor {
    fun doQueryCurrentUserPost(): Single<List<Post>>
    fun doGetCurrentUserModel(): Single<User>
    fun doQueryCurrentUserMoment(): Single<List<Moment>>

}

interface IProfilePresenter<V : IProfileView, I : IProfileInteractor> : IBasePresenter<V, I> {
    fun onViewPrepared()
}