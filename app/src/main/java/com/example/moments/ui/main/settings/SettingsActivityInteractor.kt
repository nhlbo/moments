package com.example.moments.ui.main.settings

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Message
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class SettingsActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ISettingsActivityInteractor {
//    override fun doPerformSendMessage(message: Message): Completable = firebaseHelper.performSendMessage(message)
//    override fun getCurrentUserId(): String = firebaseHelper.getCurrentUserId()
//    override fun doPerformListenToMessage(): Observable<List<Message>> = firebaseHelper.performListenToMessage()
}