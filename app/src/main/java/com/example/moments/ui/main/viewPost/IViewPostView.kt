package com.example.moments.ui.main.viewPost

import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.RetrieviedComment
import com.example.moments.data.model.RetrieviedRootComment
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import com.example.moments.ui.main.newsFeed.INewsFeedView
import io.reactivex.Completable
import io.reactivex.Single

interface IViewPostView : IBaseView {
    fun updatePost(post: RetrievedPost)
    fun updatePostComment(input: List<RetrieviedRootComment>)
    fun updateComment(comment: RetrieviedRootComment)
    fun updateReply(reply: RetrieviedComment)
}

interface IViewPostInteractor : IBaseInteractor {
    fun doQueryFeedPost(postId: String): Single<RetrievedPost>
    fun doQueryPostComment(postId: String): Single<List<RetrieviedRootComment>>
    fun doLikePost(postId: String): Completable
    fun doUnlikePost(postId: String): Completable
    fun doAddComment(postId: String, content: String): Single<RetrieviedRootComment>
    fun doAddReply(postId: String, commentId: String, content: String): Single<RetrieviedComment>
}

interface IViewPostPresenter<V : IViewPostView, I : IViewPostInteractor> :
    IBasePresenter<V, I> {
    fun onViewPrepared(postId: String)
    fun onLikePost(postId: String)
    fun onUnlikePost(postId: String)
    fun onUploadComment(postId: String, content:String)
    fun onUploadReply(postId: String, parentCommentId: String, content:String)
}