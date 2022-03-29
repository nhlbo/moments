package com.example.moments.ui.start

import com.example.moments.data.firebase.FirebaseAuthHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.example.moments.util.AppConstants
import javax.inject.Inject

class StartActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseAuthHelper: FirebaseAuthHelper
) : BaseInteractor(preferenceHelper, firebaseAuthHelper), IStartActivityInteractor {
}