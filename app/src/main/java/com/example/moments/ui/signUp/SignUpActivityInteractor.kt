package com.example.moments.ui.signUp

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import javax.inject.Inject

class SignUpActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ISignUpActivityInteractor {
    override fun doSignUp(email: String, username: String, password: String): Completable =
        firebaseHelper.performEmailAndPasswordRegister(email, username, password)
}