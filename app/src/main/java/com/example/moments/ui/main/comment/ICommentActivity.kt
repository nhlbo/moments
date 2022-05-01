package com.example.moments.ui.main.comment

import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.RetrieviedRootComment
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Single

interface ICommentActivityView : IBaseView {
}

interface ICommentActivityInteractor : IBaseInteractor {
    fun doQueryPostComment(postId: String): Single<List<RetrieviedRootComment>>
    fun doQueryPost(postId: String): Single<RetrievedPost>
    fun doAddComment(postId: String, content: String): Completable
    fun doAddReply(postId: String, commentId: String, content: String): Completable
}

interface ICommentActivityPresenter<V : ICommentActivityView, I : ICommentActivityInteractor> :
    IBasePresenter<V, I> {

}