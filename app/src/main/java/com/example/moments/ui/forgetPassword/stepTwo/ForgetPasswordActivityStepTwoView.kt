package com.example.moments.ui.forgetPassword.stepTwo

import android.content.Intent
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.forgetPassword.stepThree.ForgetPasswordActivityStepThreeView
import kotlinx.android.synthetic.main.activity_forget_password_step_two.*
import javax.inject.Inject

class ForgetPasswordActivityStepTwoView : BaseActivity(), IForgetPasswordActivityStepTwoView {

    @Inject
    lateinit var presenter: IForgetPasswordActivityStepTwoPresenter<IForgetPasswordActivityStepTwoView, IForgetPasswordActivityStepTwoInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_step_two)
        presenter.onAttach(this)
        setOnClickListener()
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun openForgetPasswordActivityStepThree(email: String) {
        val intent: Intent = Intent(this, ForgetPasswordActivityStepThreeView::class.java)
        startActivity(intent)
    }

    override fun showValidationMessage(errorCode: Int) {
        TODO("Not yet implemented")
    }

    private fun setOnClickListener() {
        val intent: Intent = intent
        val forgetPasswordEmail = intent.getStringExtra("forget_password_email")
        btnForgetPasswordStepTwoContinue.setOnClickListener {
            presenter.onForgetPasswordStepTwoContinueClicked(
                forgetPasswordEmail!!,
                etForgetPasswordStepTwoOTP.text.toString()
            )
        }
    }
}