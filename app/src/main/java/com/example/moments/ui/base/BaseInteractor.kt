package com.example.moments.ui.base

import com.example.moments.data.firebase.IFirebaseAuthHelper
import com.example.moments.data.preference.IPreferenceHelper
import com.example.moments.util.AppConstants

open class BaseInteractor() : IBaseInteractor {
    protected lateinit var preferenceHelper: IPreferenceHelper
    protected lateinit var firebaseAuthHelper: IFirebaseAuthHelper

    constructor(
        preferenceHelper: IPreferenceHelper,
        firebaseAuthHelper: IFirebaseAuthHelper
    ) : this() {
        this.preferenceHelper = preferenceHelper
        this.firebaseAuthHelper = firebaseAuthHelper
    }

    override fun isUserLoggedIn() =
        this.preferenceHelper.getCurrentUserLoggedInMode() != AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type

    override fun performUserLogout() {
        this.firebaseAuthHelper.performLogout()
    }
}