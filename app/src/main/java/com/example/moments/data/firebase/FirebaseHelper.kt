package com.example.moments.data.firebase

import android.net.Uri
import com.example.moments.data.model.Message
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.di.FirebaseAuthInstance
import com.example.moments.di.FirebaseCloudStorageInstance
import com.example.moments.di.FirebaseFirestoreInstance
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
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

    override fun performGoogleLogin(credential: AuthCredential): Completable =
        Completable.create { emitter ->
            firebaseAuth.signInWithCredential(credential).addOnSuccessListener { user ->
                firebaseFirestore.collection("user")
                    .document(user.user!!.uid)
                    .get()
                    .addOnSuccessListener { doc ->
                        if(!doc.exists()) {
                            firebaseFirestore.document("user/${getCurrentUserId()}")
                                .set(
                                    User(
                                        username = user.user!!.email!!.substring(
                                            0,
                                            user.user!!.email!!.indexOf('@')
                                        ), email = user.user!!.email!!
                                    ), SetOptions.merge()
                                )
                                .addOnSuccessListener {
                                    emitter.onComplete()
                                }
                                .addOnFailureListener {
                                    emitter.onError(it)
                                }
                        } else {
                            emitter.onComplete()
                        }
                    }
            }.addOnFailureListener {
                emitter.onError(it)
            }
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
                                        .set(
                                            User(username = username, email = email),
                                            SetOptions.merge()
                                        )
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

    override fun getCurrentUserModel(): Single<User> =
        Single.create { emitter ->
            firebaseFirestore.document("user/${getCurrentUserId()}")
                .get()
                .addOnSuccessListener { document ->
                    emitter.onSuccess(document.toObject(User::class.java)!!)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun performQueryUserByUsername(username: String): Observable<List<User>> =
        Observable.create { emitter ->
            val upperBound = username.substring(0, username.length - 1) + username.last().inc()
            firebaseFirestore.collection("user")
                .whereGreaterThanOrEqualTo("username", username)
                .whereLessThan("username", upperBound)
                .get()
                .addOnSuccessListener { documents ->
                    emitter.onNext(documents.toObjects(User::class.java)!!)
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun performQueryUserByIds(ids: List<String>): Single<List<User>> =
        Single.create { emitter ->
            firebaseFirestore.collection("user")
                .whereIn(FieldPath.documentId(), ids)
                .get()
                .addOnSuccessListener { documents ->
                    emitter.onSuccess(documents.toObjects(User::class.java)!!)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                }
        }

    override fun performQueryUserByReference(user: DocumentReference): Single<User> =
        Single.create { emitter ->
            user.get()
                .addOnSuccessListener {
                    emitter.onSuccess(it.toObject(User::class.java)!!)
                }
                .addOnFailureListener { emitter.onError(it) }
        }

    override fun performFollowUser(userId: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/user/$userId").get()
                .addOnSuccessListener { snapshot ->
                    firebaseFirestore.document("/user/${getCurrentUserId()}/following/$userId")
                        .set(hashMapOf("accepted" to !(snapshot?.data!!["private"] as Boolean)))
                        .addOnSuccessListener {
                            firebaseFirestore.document("/user/${userId}/follower/${getCurrentUserId()}")

                            emitter.onComplete()
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
                }
        }

    override fun performChangePassword(oldPassword: String, newPassword: String): Completable =
        Completable.create { emitter ->
            val user = getCurrentUser()!!
            user.reauthenticate(EmailAuthProvider.getCredential(user.email!!, oldPassword))
                .addOnSuccessListener {
                    user.updatePassword(newPassword).addOnSuccessListener {
                        emitter.onComplete()
                    }.addOnFailureListener {
                        emitter.onError(it)
                    }
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performQueryFollowingUser(): Single<List<User>> =
        Single.create { emitter ->
            firebaseFirestore.collection("/user/${getCurrentUserId()}/following").get()
                .addOnSuccessListener { followingUser ->
                    val listIdFollowing = followingUser.documents.map { it.id }
                    if (listIdFollowing.isEmpty()) {
                        emitter.onSuccess(listOf())
                        return@addOnSuccessListener
                    }
                    firebaseFirestore.collection("/user")
                        .whereIn(FieldPath.documentId(), listIdFollowing).get()
                        .addOnSuccessListener { listUserDetail ->
                            emitter.onSuccess(listUserDetail.toObjects(User::class.java)!!)
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

    override fun performQueryFeedPost(): Single<List<Post>> =
        Single.create { emitter ->
            firebaseFirestore.collection("user/${getCurrentUserId()}/following")
                .whereEqualTo("accepted", true)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val listUserFollowing: MutableList<DocumentReference> =
                        querySnapshot.map { firebaseFirestore.document("user/${it.id}") }
                            .toMutableList()
                    listUserFollowing.add(firebaseFirestore.document("user/${getCurrentUserId()}"))
                    firebaseFirestore.collection("post")
                        .whereIn("creator", listUserFollowing)
                        .orderBy("createdAt", Query.Direction.DESCENDING)
                        .get()
                        .addOnSuccessListener { postSnapshot ->
                            val listPost =
                                postSnapshot.documents.map { it.toObject(Post::class.java)!! }
                            emitter.onSuccess(listPost)
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

    override fun performQueryLikedPostUser(postId: String): Single<List<User>> =
        Single.create { emitter ->
            firebaseFirestore.collection("/post/$postId/like").get()
                .addOnSuccessListener { likedUser ->
                    val res: MutableList<User> = mutableListOf()
                    firebaseFirestore.collection("user")
                        .whereIn(FieldPath.documentId(), likedUser.documents.map { it.id })
                        .get()
                        .addOnSuccessListener { listUser ->
                            for (doc in listUser.documents) {
                                res.add(doc.toObject(User::class.java)!!)
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

    override fun performUploadListMedia(listMedia: List<ByteArray>): Observable<Uri> =
        Observable.fromIterable(listMedia)
            .flatMapSingle { media ->
                performUploadMedia(media)
            }

    override fun performQueryIsLikedPost(postId: String): Single<Boolean> =
        Single.create { emitter ->
            firebaseFirestore.document("/post/$postId/like").get()
                .addOnSuccessListener {
                    emitter.onSuccess(it.exists())
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performUploadMedia(media: ByteArray): Single<Uri> =
        Single.create { emitter ->
            val ref = firebaseStorage.reference.child("images/${UUID.randomUUID()}.jpeg")
            ref.putBytes(media, storageMetadata { contentType = "image/jpeg" })
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            emitter.onError(it)
                        }
                    }
                    ref.downloadUrl
                }
                .addOnSuccessListener {
                    emitter.onSuccess(it)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performAddPost(
        caption: String,
        media: List<String>
    ): Single<Post> =
        Single.create { emitter ->
            firebaseFirestore.collection("/post")
                .add(
                    Post(
                        caption = caption,
                        creator = firebaseFirestore.document("/user/${getCurrentUserId()}"),
                        listMedia = media
                    )
                )
                .addOnSuccessListener { docRef ->
                    docRef.get().addOnSuccessListener { docSnapshot ->
                        emitter.onSuccess(docSnapshot.toObject(Post::class.java)!!)
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

    override fun performSendMessage(message: Message): Completable =
        Completable.create { emitter ->
            firebaseFirestore.collection("/message/${message.fromId}/${message.toId}")
                .add(message)
                .addOnSuccessListener {
                    firebaseFirestore.collection("/message/${message.toId}/${message.fromId}")
                        .add(message)
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
                    firebaseFirestore.document("/message/latest-message/${message.fromId}/${message.toId}")
                        .set(message)
                    firebaseFirestore.document("/message/latest-message/${message.toId}/${message.fromId}")
                        .set(message)
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performListenToMessage(userId: String): Observable<List<Message>> =
        Observable.create { emitter ->
            firebaseFirestore.collection("/message/${getCurrentUserId()}/${userId}")
                .orderBy("timeStamp", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        emitter.onError(e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        emitter.onNext(snapshot.toObjects(Message::class.java))
                    }
                }
        }

    override fun performQueryCurrentUserPost(): Single<List<Post>> =
        Single.create { emitter ->
            firebaseFirestore.collection("post")
                .whereEqualTo("creator", firebaseFirestore.document("user/${getCurrentUserId()}"))
                .get()
                .addOnSuccessListener { listPost ->
                    emitter.onSuccess(listPost.documents.map { it.toObject(Post::class.java)!! })
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    override fun performListenToLatestMessage(): Observable<List<Message>> =
        Observable.create { emitter ->
            firebaseFirestore.collection("/message/latest-message/${getCurrentUserId()}/")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        emitter.onError(e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        emitter.onNext(snapshot.toObjects(Message::class.java))
                    }
                }
        }

    override fun performEditProfile(username: String, bio: String): Completable =
        Completable.create { emitter ->
            firebaseFirestore.document("/user/${getCurrentUserId()}")
                .update(mapOf("username" to username, "bio" to bio))
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
}