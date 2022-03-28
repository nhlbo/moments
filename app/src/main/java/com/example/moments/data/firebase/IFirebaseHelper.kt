package com.example.moments.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

interface IFirebaseHelper {

    fun performEmailAndPasswordLogin(email:String, password: String): Task<FirebaseAuth>

    fun performGoogleLogin()

}