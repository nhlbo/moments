package com.example.moments.service

import com.example.moments.data.preference.PreferenceHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import javax.inject.Inject

class MessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        preferenceHelper.setFCMToken(token)
        super.onNewToken(token)
    }
}