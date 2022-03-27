package com.example.moments.ui.forgetPassword.stepOne

import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import javax.inject.Inject

class ForgetPasswordActivityStepOneInteractor @Inject constructor(preferenceHelper: PreferenceHelper): BaseInteractor(preferenceHelper), IForgetPasswordActivityStepOneInteractor{
    override fun doRequestForgetPassword(email: String) {
        TODO("Not yet implemented")
    }
}