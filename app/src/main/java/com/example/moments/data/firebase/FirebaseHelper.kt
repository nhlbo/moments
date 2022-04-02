package com.example.moments.data.firebase

import android.util.Log
import com.example.moments.data.model.User
import com.example.moments.di.FirebaseAuthInstance
import com.example.moments.di.FirebaseFirestoreInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class FirebaseHelper @Inject constructor(
    @FirebaseAuthInstance private val firebaseAuth: FirebaseAuth,
    @FirebaseFirestoreInstance private val firebaseFirestore: FirebaseFirestore
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
                        if (task.result == null) {
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener { authTask ->
                                    firebaseFirestore.collection("user")
                                        .document(firebaseAuth.currentUser!!.uid)
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
}