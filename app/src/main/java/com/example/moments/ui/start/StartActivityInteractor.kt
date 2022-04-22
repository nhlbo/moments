package com.example.moments.ui.start

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Single
import javax.inject.Inject

class StartActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IStartActivityInteractor {
}