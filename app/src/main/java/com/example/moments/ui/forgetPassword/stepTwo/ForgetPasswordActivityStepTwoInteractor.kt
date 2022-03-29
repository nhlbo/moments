package com.example.moments.ui.forgetPassword.stepTwo

import com.example.moments.data.firebase.FirebaseAuthHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import javax.inject.Inject

class ForgetPasswordActivityStepTwoInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseAuthHelper: FirebaseAuthHelper
) : BaseInteractor(preferenceHelper, firebaseAuthHelper), IForgetPasswordActivityStepTwoInteractor