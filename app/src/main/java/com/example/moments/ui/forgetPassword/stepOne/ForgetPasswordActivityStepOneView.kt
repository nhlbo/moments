package com.example.moments.ui.forgetPassword.stepOne

import android.content.Intent
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.forgetPassword.stepTwo.ForgetPasswordActivityStepTwoView
import kotlinx.android.synthetic.main.activity_forget_password_step_one.*
import javax.inject.Inject

class ForgetPasswordActivityStepOneView : BaseActivity(), IForgetPasswordActivityStepOneView {

    @Inject
    lateinit var presenter: IForgetPasswordActivityStepOnePresenter<IForgetPasswordActivityStepOneView, IForgetPasswordActivityStepOneInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_step_one)
        presenter.onAttach(this)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        btnForgetPasswordStepOneContinue.setOnClickListener {
            presenter.onForgetPasswordStepOneContinueClicked(etForgetPasswordEmail.text.toString())
        }
    }

    override fun openForgetPasswordActivityStepTwo(email: String) {
        val intent: Intent = Intent(this, ForgetPasswordActivityStepTwoView::class.java)
        intent.putExtra("forget_password_email", email)
        startActivity(intent)
    }

    override fun showValidationMessage(errorCode: Int) {
        TODO("Not yet implemented")
    }
}