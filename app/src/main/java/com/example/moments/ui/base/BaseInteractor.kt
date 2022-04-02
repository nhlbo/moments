package com.example.moments.ui.base

import com.example.moments.data.firebase.IFirebaseHelper
import com.example.moments.data.preference.IPreferenceHelper
import com.example.moments.util.AppConstants

open class BaseInteractor() : IBaseInteractor {
    protected lateinit var preferenceHelper: IPreferenceHelper
    protected lateinit var firebaseHelper: IFirebaseHelper

    constructor(
        preferenceHelper: IPreferenceHelper,
        firebaseHelper: IFirebaseHelper
    ) : this() {
        this.preferenceHelper = preferenceHelper
        this.firebaseHelper = firebaseHelper
    }

    override fun isUserLoggedIn() =
        this.firebaseHelper.isUserLoggedIn()

    override fun performUserLogout() {
        this.firebaseHelper.performLogout()
    }

    override fun updateUserLoginStatus(loggedInMode: AppConstants.LoggedInMode) =
        this.preferenceHelper.setCurrentUserLoggedInMode(loggedInMode)
}