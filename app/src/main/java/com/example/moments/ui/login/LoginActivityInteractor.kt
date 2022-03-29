package com.example.moments.ui.login

import com.example.moments.data.firebase.FirebaseAuthHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.example.moments.util.AppConstants
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LoginActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseAuthHelper: FirebaseAuthHelper
) : BaseInteractor(preferenceHelper, firebaseAuthHelper), ILoginActivityInteractor {

    override fun observeAuthStateChange(): Observable<Boolean> =
        firebaseAuthHelper.observeAuthState()

    override fun doServerLogin(email: String, password: String): Completable =
        firebaseAuthHelper.performEmailAndPasswordLogin(email, password)

    override fun doGoogleLogin(): Observable<Any> {
        TODO("Not yet implemented")
    }

    override fun updateUserLoginStatus(loggedInMode: AppConstants.LoggedInMode) {
        preferenceHelper.setCurrentUserLoggedInMode(loggedInMode)
    }
}