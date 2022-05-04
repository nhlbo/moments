package com.example.moments.data.firebase

import android.net.Uri
import com.example.moments.data.model.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IFirebaseHelper {

    fun performEmailAndPasswordLogin(email: String, password: String): Completable

    fun performGoogleLogin(credential: AuthCredential): Completable

    fun performEmailAndPasswordRegister(
        email: String,
        username: String,
        password: String
    ): Completable

    fun getCurrentUser(): FirebaseUser?

    fun getCurrentUserModel(): Single<User>

    fun getCurrentUserId(): String

    fun isUserLoggedIn(): Boolean

    fun performLogout()

    fun performPasswordResetRequest(email: String): Completable

    fun performQueryUserByUsername(username: String): Observable<List<User>>

    fun performQueryUserByVuforiaId(vuforiaId: String): Single<User>

    fun performQueryUserByIds(ids: List<String>): Single<List<User>>

    fun performQueryUserByReference(user: DocumentReference): Single<User>

    fun performQueryUserById(userId: String): Single<User>

    fun performFollowUser(userId: String): Completable

    fun performUnfollowUser(userId: String): Completable

    fun performQueryUserIsFollowed(userId: String): Single<Boolean>

    fun performAcceptFollower(userId: String): Completable

    fun performQueryFeedPost(): Single<List<Post>>

    fun performQueryIsLikedPost(postId: String): Single<Boolean>

    fun performLikePost(postId: String): Completable

    fun performUnlikePost(postId: String): Completable

    fun performQueryLikedPostUser(postId: String): Single<List<User>>

    fun performQueryPostIsLiked(postId: String): Single<Boolean>

    fun performDeletePost(postId: String): Completable

    fun performUploadListImage(listImage: List<ByteArray>): Observable<Uri>

    fun performUploadListVideo(listVideo: List<ByteArray>): Observable<Uri>

    fun performUploadMedia(media: ByteArray, contentType: String = "image/jpeg"): Single<Uri>

    fun performAddPost(caption: String, media: List<String>): Single<Post>

    fun performBookmarkPost(postId: String): Completable

    fun performUnBookmarkPost(postId: String): Completable

    fun performQueryBookmarkPost(): Single<List<DocumentSnapshot>>

    fun performQueryPostIsBookmarked(postId: String): Single<Boolean>

    fun performQueryFollowingCurrentUser(): Single<List<User>>

    fun performQueryFollowingUser(userId: String): Single<List<User>>

    fun performQueryFollowerCurrentUser(): Single<List<User>>

    fun performQueryFollower(userId: String): Single<List<User>>

    fun performSendMessage(message: Message): Completable

    fun performListenToMessage(userId: String): Observable<List<Message>>

    fun performQueryCurrentUserPost(): Single<List<Post>>

    fun performQueryUserPostByUserReference(userRef: DocumentReference): Single<List<Post>>

    fun performQueryUserPostByUserId(userId: String): Single<List<Post>>

    fun performChangePassword(oldPassword: String, newPassword: String): Completable

    fun performListenToLatestMessage(): Observable<List<Message>>

    fun performEditProfile(username: String, bio: String): Completable

    fun performAddNotification(
        toUser: String,
        type: String,
        caption: String,
        media: String,
        postId: String?
    ): Task<DocumentReference>

    fun performQueryNotification(): Single<List<Notification>>

    fun performAddCommentToPost(postId: String, content: String): Single<Comment>

    fun performReplyComment(postId: String, commentId: String, content: String): Single<Comment>

    fun performQueryPostComment(postId: String): Single<List<Comment>>

    fun performQueryPostCommentReply(postId: String, commentId: String): Single<List<Comment>>

    fun performQueryPostById(postId: String): Single<Post>

    fun performQueryPostByReference(postRef: DocumentReference): Single<Post>

    fun getCurrentUserReference(): DocumentReference

    fun performUpdateCurrentUserAvatar(avatarUri: String): Completable

    fun performAddMoment(caption: String, media: String): Completable

    fun performQueryUserMoment(userId: String): Single<List<Moment>>

    fun performQueryCurrentUserMoment(): Single<List<Moment>>

    fun performQueryFeedMoment(): Single<List<Moment>>

    fun performLikeMoment(momentId: String): Completable

    fun performUnlikeMoment(momentId: String): Completable

    fun performQueryMomentIsLiked(momentId: String): Single<Boolean>
}