package com.example.moments.ui.forgetPassword.stepTwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import javax.inject.Inject

class ForgetPasswordActivityStepTwoView : BaseActivity(), IForgetPasswordActivityStepTwoView {

    @Inject
    lateinit var presenter: IForgetPasswordActivityStepTwoPresenter<IForgetPasswordActivityStepTwoView, IForgetPasswordActivityStepTwoInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_step_two)
        presenter.onAttach(this)

    }

    override fun openForgetPasswordActivityStepThree(email: String) {
        TODO("Not yet implemented")
    }

    override fun showValidationMessage(errorCode: Int) {
        TODO("Not yet implemented")
    }
}