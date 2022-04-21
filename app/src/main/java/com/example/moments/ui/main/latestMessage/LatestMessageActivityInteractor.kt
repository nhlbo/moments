package com.example.moments.ui.main.latestMessage

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.example.moments.ui.main.newMessage.INewMessageActivityInteractor
import io.reactivex.Single
import javax.inject.Inject

class LatestMessageActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ILatestMessageActivityInteractor {
    override fun doQueryLatestMessage(): Single<List<Pair<User, Message>>> = firebaseHelper.performQueryLatestMessage()

}