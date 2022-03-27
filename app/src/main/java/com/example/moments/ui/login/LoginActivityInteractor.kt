package com.example.moments.ui.login

import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.example.moments.util.AppConstants
import io.reactivex.Observable
import javax.inject.Inject

class LoginActivityInteractor @Inject constructor(preferenceHelper: PreferenceHelper): BaseInteractor(preferenceHelper), ILoginActivityInteractor {
    override fun doServerLogin(email: String, password: String): Observable<Any> {
        TODO("Not yet implemented")
    }

    override fun doGoogleLogin(): Observable<Any> {
        TODO("Not yet implemented")
    }

    override fun updateUserInSharedPref(
        loginResponse: Any,
        loggedInMode: AppConstants.LoggedInMode
    ) {
        TODO("Not yet implemented")
    }
}