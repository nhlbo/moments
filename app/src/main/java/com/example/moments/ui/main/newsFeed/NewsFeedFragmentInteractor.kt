package com.example.moments.ui.main.newsFeed

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NewsFeedFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), INewsFeedInteractor {
    override fun doQueryFeedPost(): Single<List<RetrievedPost>> =
        firebaseHelper.performQueryFeedPost().flattenAsObservable { it }
            .flatMapSingle { post ->
                firebaseHelper.performQueryUserByReference(post.creator!!).map { user ->
                    RetrievedPost(post, user)
                }
            }
            .flatMapSingle{ post ->
                firebaseHelper.performQueryPostIsLiked(post.id).map{ isLiked ->
                    post.liked = isLiked
                    post
                }
            }
            .flatMapSingle{ post ->
                firebaseHelper.performQueryPostIsBookmarked(post.id).map{ bookmarked ->
                    post.bookmarked = bookmarked
                    post
                }
            }
            .toList()

    override fun doLikePost(postId: String): Completable =
        firebaseHelper.performLikePost(postId)

    override fun doUnlikePost(postId: String): Completable =
        firebaseHelper.performUnlikePost(postId)

    override fun doBookmarkPost(postId: String): Completable =
        firebaseHelper.performBookmarkPost(postId)

    override fun doUnBookmarkPost(postId: String): Completable =
        firebaseHelper.performUnBookmarkPost(postId)

    override fun doGetCurrentUserModel(): Single<User> = firebaseHelper.getCurrentUserModel()

    override fun doDeletePost(postId: String): Completable =
        firebaseHelper.performDeletePost(postId)

}