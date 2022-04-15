package com.example.moments.data.firebase

import android.net.Uri
import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IFirebaseHelper {

    fun performEmailAndPasswordLogin(email: String, password: String): Completable

    fun performGoogleLogin(): Completable

    fun performEmailAndPasswordRegister(
        email: String,
        username: String,
        password: String
    ): Completable

    fun getCurrentUser(): FirebaseUser?

    fun getCurrentUserId(): String

    fun isUserLoggedIn(): Boolean

    fun performLogout()

    fun performPasswordResetRequest(email: String): Completable

    fun performQueryUserByUsername(username: String): Single<List<DocumentSnapshot>>

    fun performFollowUser(userId: String): Completable

    fun performAcceptFollower(userId: String): Completable

    fun performQueryFeedPost(): Single<List<DocumentSnapshot>>

    fun performLikePost(postId: String): Completable

    fun performUnlikePost(postId: String): Completable

    fun performQueryLikedPostUser(postId: String): Single<List<DocumentSnapshot>>

    fun performDeletePost(postId: String): Completable

    fun performUploadMedia(listMedia: ArrayList<ByteArray>): Observable<Uri>

    fun performAddPost(caption: String, media: ArrayList<String>): Single<DocumentSnapshot>

    fun performBookmarkPost(postId: String): Completable

    fun performUnBookmarkPost(postId: String): Completable

    fun performQueryBookmarkPost(): Single<List<DocumentSnapshot>>

    fun performQueryFollowingUser(): Single<List<User>>

    fun performSendMessage(message: Message): Completable

    fun performListenToMessage(): Observable<List<Message>>
}