package com.example.moments.data.firebase

import com.example.moments.data.model.User
import com.example.moments.di.FirebaseAuthInstance
import com.example.moments.di.FirebaseFirestoreInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single
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
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseFirestore.collection("user")
                            .document(firebaseAuth.currentUser!!.uid).set(User(username, email))
                            .addOnSuccessListener {
                                emitter.onComplete()
                            }
                    } else {
                        emitter.onError(task.exception ?: Exception("Failed to register"))
                    }
                }
        }

    override fun performLogout() {
        firebaseAuth.signOut()
    }

    override fun performPasswordResetRequest(email: String): Completable =
        Completable.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(email, )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Exception("Failed to request"))
                    }
                }
        }

    override fun performVerifyResetPasswordConfirmationCode(code: String): Single<String> =
        Single.create { emitter ->
            firebaseAuth.verifyPasswordResetCode(code)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(task.result)
                    } else {
                        emitter.onError(task.exception ?: Exception("Failed to request"))
                    }
                }
        }

    override fun performConfirmResetPassword(code: String, newPassword: String): Completable =
        Completable.create { emitter ->
            firebaseAuth.confirmPasswordReset(code, newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Exception("Failed to reset password"))
                    }
                }
        }


    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
}