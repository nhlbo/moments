package com.example.moments.ui.base

import com.example.moments.data.firebase.IFirebaseHelper
import com.example.moments.data.preference.IPreferenceHelper
import com.example.moments.util.AppConstants

open class BaseInteractor(
    protected var preferenceHelper: IPreferenceHelper,
    protected var firebaseHelper: IFirebaseHelper
) : IBaseInteractor {

    override fun isUserLoggedIn() =
        this.firebaseHelper.isUserLoggedIn()

    override fun performUserLogout() {
        this.firebaseHelper.performLogout()
    }
}