package com.example.moments.data.preference

import com.example.moments.util.AppConstants

interface IPreferenceHelper {
    fun getFCMToken(): String?
    fun setFCMToken(token: String)
}