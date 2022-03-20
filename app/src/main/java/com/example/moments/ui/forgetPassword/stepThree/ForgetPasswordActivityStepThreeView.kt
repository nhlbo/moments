package com.example.moments.ui.forgetPassword.stepThree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity

class ForgetPasswordActivityStepThreeView : BaseActivity(), IForgetPasswordActivityStepThreeView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_step_three)
    }

    override fun openForgetPasswordActivityStepThree(email: String) {
        TODO("Not yet implemented")
    }

    override fun showValidationMessage(errorCode: Int) {
        TODO("Not yet implemented")
    }
}