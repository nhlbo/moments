package com.example.moments.ui.signUp

import com.example.moments.data.firebase.FirebaseAuthHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

class SignUpActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseAuthHelper: FirebaseAuthHelper
) : BaseInteractor(preferenceHelper, firebaseAuthHelper), ISignUpActivityInteractor {
    override fun doSignUp(email: String, username: String, password: String): Observable<Any> {
        TODO("Not yet implemented")
    }
}