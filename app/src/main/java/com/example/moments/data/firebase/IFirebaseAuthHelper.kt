package com.example.moments.data.firebase

import io.reactivex.Completable
import io.reactivex.Observable

interface IFirebaseAuthHelper {

    fun performEmailAndPasswordLogin(email: String, password: String): Completable

    fun performGoogleLogin(): Completable

    fun performEmailAndPasswordRegister(email: String, password: String): Completable

    fun observeAuthState(): Observable<Boolean>

    fun performLogout()

}