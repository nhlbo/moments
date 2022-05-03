package com.example.moments.ui.main.comment

import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.RetrieviedComment
import com.example.moments.data.model.RetrieviedRootComment
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Completable
import io.reactivex.Single

interface ICommentActivityView : IBaseView {
    fun updatePostComment(input: List<RetrieviedRootComment>)
    fun updatePost(input: RetrievedPost)
    fun updateComment(comment: RetrieviedRootComment)
    fun updateReply(reply: RetrieviedComment)
}

interface ICommentActivityInteractor : IBaseInteractor {
    fun doQueryPostComment(postId: String): Single<List<RetrieviedRootComment>>
    fun doQueryPost(postId: String): Single<RetrievedPost>
    fun doAddComment(postId: String, content: String): Single<RetrieviedRootComment>
    fun doAddReply(postId: String, commentId: String, content: String): Single<RetrieviedComment>
}

interface ICommentActivityPresenter<V : ICommentActivityView, I : ICommentActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPreparedView(postId: String)
    fun onUploadComment(postId: String, content:String)
    fun onUploadReply(postId: String, parentCommentId: String, content:String)
}