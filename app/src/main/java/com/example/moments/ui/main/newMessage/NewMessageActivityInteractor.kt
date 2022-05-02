package com.example.moments.ui.main.newMessage

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class NewMessageActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), INewMessageActivityInteractor {
    override fun doQueryFollowingUser(): Single<List<User>> = firebaseHelper.performQueryFollowingCurrentUser()

}