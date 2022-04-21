package com.example.moments.ui.main.editProfile

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.example.moments.ui.main.EditProfile.IEditProfileActivityInteractor
import javax.inject.Inject

class EditProfileActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IEditProfileActivityInteractor {
    override fun doPerformLogOut() = firebaseHelper.performLogout()
}