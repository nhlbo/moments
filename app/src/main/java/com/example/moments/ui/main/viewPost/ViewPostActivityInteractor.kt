package com.example.moments.ui.main.viewPost

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.RetrieviedComment
import com.example.moments.data.model.RetrieviedRootComment
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ViewPostActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IViewPostInteractor {
    override fun doQueryFeedPost(postId: String) : Single<RetrievedPost> =
        firebaseHelper.performQueryPostById(postId).flatMap { post ->
            firebaseHelper.performQueryUserByReference(post.creator!!).map { user ->
                RetrievedPost(post, user)
            }
        }

    override fun doQueryPostComment(postId: String): Single<List<RetrieviedRootComment>> =
        firebaseHelper.performQueryPostComment(postId).flattenAsObservable { it }
        .flatMapSingle { rootComment ->
            firebaseHelper.performQueryUserByReference(rootComment.creator!!).map { user ->
                RetrieviedRootComment(rootComment, user)
            }
        }
        .flatMapSingle { retrievedRootComment ->
            firebaseHelper.performQueryPostCommentReply(postId, retrievedRootComment.id)
                .flattenAsObservable { it }
                .flatMapSingle { comment ->
                    firebaseHelper.performQueryUserByReference(comment.creator!!).map { user ->
                        RetrieviedComment(comment, user)
                    }
                }
                .toList()
                .map { listReply ->
                    retrievedRootComment.replies = listReply
                    retrievedRootComment
                }

        }
        .toList()

    override fun doLikePost(postId: String): Completable {
        TODO("Not yet implemented")
    }

    override fun doUnlikePost(postId: String): Completable {
        TODO("Not yet implemented")
    }

    override fun doAddComment(postId: String, content: String): Single<RetrieviedRootComment> =
        firebaseHelper.performAddCommentToPost(postId, content).flatMap { comment ->
            firebaseHelper.performQueryUserByReference(comment.creator!!).map { user ->
                RetrieviedRootComment(comment, user)
            }
        }


    override fun doAddReply(
        postId: String,
        commentId: String,
        content: String
    ): Single<RetrieviedComment> =
        firebaseHelper.performReplyComment(postId, commentId, content).flatMap { comment ->
            firebaseHelper.performQueryUserByReference(comment.creator!!).map { user ->
                RetrieviedComment(comment, user)
            }
        }
}