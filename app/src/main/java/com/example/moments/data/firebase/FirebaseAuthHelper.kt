package com.example.moments.data.firebase

import com.example.moments.di.FirebaseAuthInstance
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FirebaseAuthHelper @Inject constructor(
    @FirebaseAuthInstance private val firebaseAuth: FirebaseAuth // which is Firebase.auth
) : IFirebaseAuthHelper {

    override fun performEmailAndPasswordLogin(email: String, password: String): Completable =
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

    override fun performGoogleLogin(): Completable {
        TODO("sfs")
    }

    override fun performEmailAndPasswordRegister(email: String, password: String): Completable =
        Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception ?: Exception("Failed to register"))
                    }
                }
        }

    override fun observeAuthState(): Observable<Boolean> =
        Observable.create<Boolean> { emitter ->
            val authStateListener = FirebaseAuth.AuthStateListener {
                if (!emitter.isDisposed) emitter.onNext(it.currentUser != null)
            }
            firebaseAuth.addAuthStateListener(authStateListener)
            emitter.setCancellable {
                firebaseAuth.removeAuthStateListener(authStateListener)
            }
        }

    override fun performLogout() {
        firebaseAuth.signOut()
    }

}