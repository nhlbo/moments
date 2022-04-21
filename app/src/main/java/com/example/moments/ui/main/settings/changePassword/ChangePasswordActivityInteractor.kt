package com.example.moments.ui.main.settings.changePassword

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import javax.inject.Inject

class ChangePasswordActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IChangePasswordInteractor {
    override fun doChangePassword(oldPassword: String, newPassword: String): Completable =
        firebaseHelper.performChangePassword(oldPassword, newPassword)
}