package com.example.moments.ui.forgetPassword

import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_forget_password.*
import javax.inject.Inject

class ForgetPasswordActivityView : BaseActivity(), IForgetPasswordActivityView {

    @Inject
    lateinit var presenter: IForgetPasswordActivityPresenter<IForgetPasswordActivityView, IForgetPasswordActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        presenter.onAttach(this)
        setOnClickListener()
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    private fun setOnClickListener() {
        btnSubmitResetPassword.setOnClickListener {
            presenter.onForgetPasswordClicked(etForgetPasswordEmail.text.toString())
        }
    }

    override fun backToLoginActivity() {
        finish()
    }

    override fun showValidationMessage(errorCode: Int) {
        TODO("Not yet implemented")
    }
}