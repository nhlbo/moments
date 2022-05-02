package com.example.moments.data.firebase

import android.net.Uri
import com.example.moments.data.model.*
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

    fun performQueryUserByIds(ids: List<String>): Single<List<User>>

    fun performQueryUserByReference(user: DocumentReference): Single<User>

    fun performQueryUserById(userId: String): Single<User>

    fun performFollowUser(userId: String): Completable

    fun performAcceptFollower(userId: String): Completable

    fun performQueryFeedPost(): Single<List<Post>>

    fun performQueryIsLikedPost(postId: String): Single<Boolean>

    fun performLikePost(postId: String): Completable

    fun performUnlikePost(postId: String): Completable

    fun performQueryLikedPostUser(postId: String): Single<List<User>>

    fun performDeletePost(postId: String): Completable

    fun performUploadListMedia(listMedia: List<ByteArray>): Observable<Uri>

    fun performUploadMedia(media: ByteArray): Single<Uri>

    fun performAddPost(caption: String, media: List<String>): Single<Post>

    fun performBookmarkPost(postId: String): Completable

    fun performUnBookmarkPost(postId: String): Completable

    fun performQueryBookmarkPost(): Single<List<DocumentSnapshot>>

    fun performQueryFollowingUser(): Single<List<User>>

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
    ): Completable

    fun performQueryNotification(): Single<List<Notification>>

    fun performAddCommentToPost(postId: String, content: String): Completable

    fun performReplyComment(postId: String, commentId: String, content: String): Completable

    fun performQueryPostComment(postId: String): Single<List<Comment>>

    fun performQueryPostCommentReply(postId: String, commentId: String): Single<List<Comment>>

    fun performQueryPostById(postId: String): Single<Post>

    fun performQueryPostByReference(postRef: DocumentReference): Single<Post>

    fun getCurrentUserReference(): DocumentReference

    fun performUpdateCurrentUserAvatar(avatarUri: String): Completable
}