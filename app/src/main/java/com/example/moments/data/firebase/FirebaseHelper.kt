package com.example.moments.data.firebase

import android.net.Uri
import android.util.Log
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.di.FirebaseAuthInstance
import com.example.moments.di.FirebaseCloudStorageInstance
import com.example.moments.di.FirebaseFirestoreInstance
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
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
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result.isEmpty) { // un-used username
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener { authTask ->
                                    firebaseFirestore.collection("user")
                                        .document(getCurrentUserId())
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

    override fun performFollowUser(userId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/user/$userId").get()
                .addOnSuccessListener { snapshot ->
                    firebaseFirestore.document("/user/${getCurrentUserId()}/following/$userId")
                        .set(hashMapOf("accepted" to !(snapshot?.data!!["private"] as Boolean)))
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
                }
        }

    override fun performAcceptFollower(userId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/user/$userId/following/${getCurrentUserId()}")
                .update("accepted", true)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performQueryFeedPost(): Single<QuerySnapshot> =
        Single.create { emitter ->
            firebaseFirestore.collection("user/${getCurrentUserId()}/following")
                .whereEqualTo("accepted", true)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val listUserFollowing: List<DocumentReference> =
                        querySnapshot.map { firebaseFirestore.document("/user/${it.id}") }
                    firebaseFirestore.collection("post")
                        .whereIn("creatorId", listUserFollowing)
                        .get()
                        .addOnSuccessListener {
                            for (document in it.documents) {
                                Log.d("feed", document.data.toString())
                            }
                            emitter.onSuccess(it)
                        }
                }
        }

    override fun getCurrentUserId(): String = getCurrentUser()!!.uid

    override fun performLikePost(postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/post/$postId/like/${getCurrentUserId()}")
                .set(hashMapOf("type" to "like"))// can be change to another various reactions type
                .addOnSuccessListener {
                    firebaseFirestore.document("/post/$postId")
                        .update("likeCount", FieldValue.increment(1))
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performUnlikePost(postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/post/$postId/like/${getCurrentUserId()}")
                .delete()
                .addOnSuccessListener {
                    firebaseFirestore.document("/post/$postId")
                        .update("likeCount", FieldValue.increment(-1))
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performQueryLikedPostUser(postId: String): Single<List<DocumentSnapshot>> =
        Single.create { emitter ->
            firebaseFirestore.collection("/post/$postId/like").get()
                .addOnSuccessListener { likedPosts ->
                    val res: MutableList<DocumentSnapshot> = mutableListOf()
                    for (post in likedPosts.documents) {
                        firebaseFirestore.document("/user/${post.id}").get()
                            .addOnSuccessListener {
                                res.add(it)
                            }
                    }
                    emitter.onSuccess(res.toList())
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performBookmarkPost(postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/user/${getCurrentUserId()}/bookmark/$postId")
                .set(hashMapOf("createdAt" to Timestamp.now()))
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performUnBookmarkPost(postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/user/${getCurrentUserId()}/bookmark/$postId")
                .delete()
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performQueryBookmarkPost(): Single<List<DocumentSnapshot>> =
        Single.create { emitter ->
            firebaseFirestore.collection("/user/${getCurrentUserId()}/bookmark")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { bookmarkedPosts ->
                    val res: MutableList<DocumentSnapshot> = mutableListOf()
                    for (post in bookmarkedPosts.documents) {
                        firebaseFirestore.document("/post/${post.id}")
                            .get()
                            .addOnSuccessListener {
                                if (it.exists()) {
                                    res.add(it)
                                }
                            }
                    }
                    emitter.onSuccess(res.toList())
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performUploadMedia(listMedia: ArrayList<ByteArray>): Observable<Uri> =
        Observable.create { emitter ->
            val ref = firebaseStorage.reference
            for (media in listMedia) {
                ref.child("images/${UUID.randomUUID()}.jpeg")
                    .putBytes(media, storageMetadata { contentType = "image/jpeg" })
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
            firebaseFirestore.collection("/post")
                .add(
                    Post(
                        firebaseFirestore.document("/user/${getCurrentUserId()}"),
                        caption,
                        media
                    )
                )
                .addOnSuccessListener { docRef ->
                    docRef.get().addOnSuccessListener { docSnapshot ->
                        emitter.onSuccess(docSnapshot)
                    }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performDeletePost(postId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/user/${getCurrentUserId()}/post/$postId")
                .delete()
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
}