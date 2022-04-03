package com.example.moments.data.firebase

import android.net.Uri
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.di.FirebaseAuthInstance
import com.example.moments.di.FirebaseCloudStorageInstance
import com.example.moments.di.FirebaseFirestoreInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storageMetadata
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class FirebaseHelper @Inject constructor(
    @FirebaseAuthInstance private val firebaseAuth: FirebaseAuth,
    @FirebaseFirestoreInstance private val firebaseFirestore: FirebaseFirestore,
    @FirebaseCloudStorageInstance private val firebaseStorage: FirebaseStorage
) : IFirebaseHelper {

    override fun performEmailAndPasswordLogin(
        email: String,
        password: String
    ): Completable =
        Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Exception("Failed to login!"))
                    }
                }
        }

    override fun isUserLoggedIn(): Boolean = getCurrentUser() != null

    override fun performGoogleLogin(): Completable {
        TODO("sfs")
    }

    override fun performEmailAndPasswordRegister(
        email: String,
        username: String,
        password: String
    ): Completable =
        Completable.create { emitter ->
            firebaseFirestore.collection("user").whereEqualTo("username", username)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result.isEmpty) {
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener { authTask ->
                                    firebaseFirestore.collection("user")
                                        .document(getCurrentUser()!!.uid)
                                        .set(User(username, email))
                                        .addOnSuccessListener {
                                            emitter.onComplete()
                                        }
                                }
                        } else {
                            emitter.onError(Exception("Username already used by another user!"))
                        }
                    } else {
                        emitter.onError(
                            task.exception ?: Exception("Failed to register")
                        )
                    }
                }
        }

    override fun performLogout() {
        firebaseAuth.signOut()
    }

    override fun performPasswordResetRequest(email: String): Completable =
        Completable.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Exception("Failed to request"))
                    }
                }
        }


    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override fun performQueryUserByUsername(username: String): Single<QuerySnapshot> =
        Single.create { emitter ->
            firebaseFirestore.collection("user")
                .whereGreaterThanOrEqualTo("username", username)
                .get()
                .addOnSuccessListener { documents ->
                    emitter.onSuccess(documents)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun performRequestFollowUser(userId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/requestFollow/$userId/user/${getCurrentUser()!!.uid}")
                .set(1)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performAcceptFollower(requestId: String): Completable {
        TODO("Not yet implemented")
    }

    override fun performQueryFeedPost(): Single<QuerySnapshot> {
        TODO("Not yet implemented")
    }

    override fun performLikePost(creatorId: String, postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/post/$creatorId/userPost/$postId/like/${getCurrentUser()!!.uid}")
                .set(1)
                .addOnSuccessListener {
                    firebaseFirestore.document("/post/$creatorId/userPost/$postId")
                        .update("likeCount", FieldValue.increment(1))
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performUnlikePost(creatorId: String, postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/post/$creatorId/userPost/$postId/like/${getCurrentUser()!!.uid}")
                .delete()
                .addOnSuccessListener {
                    firebaseFirestore.document("/post/$creatorId/userPost/$postId")
                        .update("likeCount", FieldValue.increment(-1))
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performUploadMedia(listMedia: ArrayList<ByteArray>): Observable<Uri> =
        Observable.create { emitter ->
            val ref = firebaseStorage.reference
            for (media in listMedia) {
                ref.child("images/${UUID.randomUUID()}.png")
                    .putBytes(media, storageMetadata { contentType = "image/png" })
                    .continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                emitter.onError(it)
                            }
                        }
                        ref.downloadUrl
                    }
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            emitter.onNext(task.result)
                    }
            }
        }


    override fun performAddPost(
        caption: String,
        media: ArrayList<String>
    ): Single<DocumentSnapshot> =
        Single.create { emitter ->
            firebaseFirestore.collection("post")
                .document(getCurrentUser()!!.uid)
                .collection("userPost")
                .add(Post(caption, media))
                .addOnSuccessListener {
                    emitter.onSuccess(it.get().result)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performDeletePost(postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.collection("post")
                .document(getCurrentUser()!!.uid)
                .collection("userPost")
                .document(postId)
                .delete()
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
}