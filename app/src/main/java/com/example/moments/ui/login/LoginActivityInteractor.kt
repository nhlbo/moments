package com.example.moments.ui.login

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.google.firebase.auth.AuthCredential
import io.reactivex.Completable
import javax.inject.Inject

class LoginActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ILoginActivityInteractor {

    override fun doServerLogin(email: String, password: String): Completable =
        firebaseHelper.performEmailAndPasswordLogin(email, password)

    override fun doGoogleLogin(credential: AuthCredential): Completable =
        firebaseHelper.performGoogleLogin(credential)
}