package com.example.moments.ui.main.latestMessage

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

class LatestMessageActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ILatestMessageActivityInteractor {
    override fun doListenToLatestMessage(): Observable<List<Pair<User, Message>>> =
        firebaseHelper.performListenToLatestMessage().flatMap { messages ->
            firebaseHelper.performQueryUserByIds(messages.map { if (it.fromId == firebaseHelper.getCurrentUserId()) it.toId else it.fromId })
                .map { users ->
                    users.zip(messages)
                }.toObservable()
        }
}