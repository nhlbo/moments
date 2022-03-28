package com.example.moments.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class FirebaseHelper : IFirebaseHelper {
    override fun performEmailAndPasswordLogin(email: String, password: String): Task<FirebaseAuth> {
        TODO("Not yet implemented")
    }

    override fun performGoogleLogin() {
        TODO("Not yet implemented")
    }

}