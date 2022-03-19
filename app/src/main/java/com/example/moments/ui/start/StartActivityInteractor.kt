package com.example.moments.ui.start

import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import javax.inject.Inject

class StartActivityInteractor @Inject constructor(preferenceHelper: PreferenceHelper) :BaseInteractor(preferenceHelper), IStartActivityInteractor {

    override fun isUserLoggedIn(): Boolean {
        return false
    }
}