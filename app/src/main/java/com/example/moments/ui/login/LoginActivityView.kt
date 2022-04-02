package com.example.moments.ui.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.forgetPassword.ForgetPasswordActivityView
import com.example.moments.ui.main.MainActivityView
import com.example.moments.ui.signUp.SignUpActivityView
import com.example.moments.util.AppConstants
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivityView : BaseActivity(), ILoginActivityView {

    @Inject
    lateinit var presenter: ILoginActivityPresenter<ILoginActivityView, ILoginActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.onAttach(this)
        setOnClickListener()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun openSignUpActivity() {
        val intent: Intent = Intent(this, SignUpActivityView::class.java)
        startActivity(intent)
    }

    override fun openMainActivity() {
        val intent: Intent = Intent(this, MainActivityView::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun openForgotPasswordActivity() {
        val intent: Intent = Intent(this, ForgetPasswordActivityView::class.java)
        startActivity(intent)
    }

    override fun showValidationMessage(errorCode: Int) {
        when (errorCode) {
            AppConstants.EMPTY_EMAIL_ERROR -> Toast.makeText(
                this,
                getString(R.string.empty_email_error_message),
                Toast.LENGTH_LONG
            ).show()
            AppConstants.INVALID_EMAIL_ERROR -> Toast.makeText(
                this,
                getString(R.string.invalid_email_error_message),
                Toast.LENGTH_LONG
            ).show()
            AppConstants.EMPTY_PASSWORD_ERROR -> Toast.makeText(
                this,
                getString(R.string.empty_password_error_message),
                Toast.LENGTH_LONG
            ).show()
            AppConstants.LOGIN_FAILURE -> Toast.makeText(
                this,
                getString(R.string.login_failure),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun setOnClickListener() {
        btnServerLogin.setOnClickListener {
            presenter.onServerLoginClicked(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
        btnGoogleLogin.setOnClickListener {
            presenter.onGoogleLoginClicked()
        }
        btnGoToSignUp.setOnClickListener {
            presenter.onGoToSignUpClicked()
        }
        tvForgetPassword.setOnClickListener {
            presenter.onGoToForgotPasswordClicked()
        }
    }
}