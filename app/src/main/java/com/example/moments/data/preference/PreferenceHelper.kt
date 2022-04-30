package com.example.moments.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.moments.di.PreferenceInfo
import com.example.moments.util.AppConstants
import javax.inject.Inject

class PreferenceHelper @Inject constructor(
    context: Context,
    @PreferenceInfo private val prefFileName: String
) : IPreferenceHelper {
    companion object {
        private val PREF_KEY_FCM_TOKEN = "PREF_KEY_FCM_TOKEN"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getFCMToken(): String? = mPrefs.getString(PREF_KEY_FCM_TOKEN, "invalid_token")

    override fun setFCMToken(token: String) = mPrefs.edit{
        putString(PREF_KEY_FCM_TOKEN, token)
    }
}