package com.example.moments.ui.signUp

import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

class SignUpActivityInteractor @Inject constructor(preferenceHelper: PreferenceHelper): BaseInteractor(preferenceHelper), ISignUpActivityInteractor {
    override fun doSignUp(email: String, username: String, password: String): Observable<Any> {
        TODO("Not yet implemented")
    }
}