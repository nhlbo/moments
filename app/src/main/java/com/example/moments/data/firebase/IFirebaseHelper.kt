package com.example.moments.data.firebase

import android.net.Uri
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

    fun isUserLoggedIn(): Boolean

    fun performLogout()

    fun performPasswordResetRequest(email: String): Completable

    fun performQueryUserByUsername(username: String): Single<QuerySnapshot>

    fun performRequestFollowUser(userId: String): Completable

    fun performAcceptFollower(requestId: String): Completable

    fun performQueryFeedPost(): Single<QuerySnapshot>

    fun performLikePost(creatorId: String, postId: String): Completable

    fun performUnlikePost(creatorId: String, postId: String): Completable

    fun performDeletePost(postId: String): Completable

    fun performUploadMedia(listMedia: ArrayList<ByteArray>): Observable<Uri>

    fun performAddPost(caption: String, media: ArrayList<String>): Single<DocumentSnapshot>
}