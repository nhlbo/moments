package com.example.moments.ui.vuforia

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class VuforiaActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IVuforiaActivityInteractor {
    override fun doPerformQueryUserByVuforiaId(vuforiaId: String): Single<User> =
        firebaseHelper.performQueryUserByVuforiaId(vuforiaId)

}