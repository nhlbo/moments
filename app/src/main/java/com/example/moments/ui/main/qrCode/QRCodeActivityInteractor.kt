package com.example.moments.ui.main.qrCode

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import javax.inject.Inject

class QRCodeActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IQRCodeActivityInteractor {
    override fun doPerformEditProfile(username: String, bio: String): Completable = firebaseHelper.performEditProfile(username, bio)
}