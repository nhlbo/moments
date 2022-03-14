package com.example.moments.ui.base

import com.example.moments.data.preference.IPreferenceHelper
import com.example.moments.util.AppConstants

open class BaseInteractor() : IBaseInteractor {
    protected lateinit var preferenceHelper: IPreferenceHelper

    constructor(preferenceHelper: IPreferenceHelper) : this() {
        this.preferenceHelper = preferenceHelper
    }

    override fun isUserLoggedIn() = this.preferenceHelper.getCurrentUserLoggedInMode() != AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type

    override fun performUserLogout() = preferenceHelper.let {
        it.setCurrentUserId(null)
        it.setAccessToken(null)
        it.setCurrentUserEmail(null)
        it.setCurrentUserLoggedInMode(AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT)
    }

}