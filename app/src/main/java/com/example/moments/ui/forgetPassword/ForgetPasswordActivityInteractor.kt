package com.example.moments.ui.forgetPassword

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import javax.inject.Inject

class ForgetPasswordActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) :
    BaseInteractor(preferenceHelper, firebaseHelper), IForgetPasswordActivityInteractor {
    override fun doRequestForgetPassword(email: String): Completable =
        firebaseHelper.performPasswordResetRequest(email)
}