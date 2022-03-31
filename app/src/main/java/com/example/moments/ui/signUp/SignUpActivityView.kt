package com.example.moments.ui.signUp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.MainActivityView
import com.example.moments.util.AppConstants
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivityView : BaseActivity(), ISignUpActivityView {

    @Inject
    lateinit var presenter: ISignUpActivityPresenter<ISignUpActivityView, ISignUpActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        presenter.onAttach(this)
        setOnClickListener()
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun openLoginActivity() {
        val intent: Intent = Intent(this, MainActivityView::class.java)
        startActivity(intent)
        finishAffinity()
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
            AppConstants.CONFIRM_PASSWORD_NOT_MATCH_ERROR -> Toast.makeText(
                this,
                getString(R.string.confirm_password_not_match_error_message),
                Toast.LENGTH_LONG
            ).show()
            AppConstants.INVALID_PASSWORD_ERROR -> Toast.makeText(
                this,
                getString(R.string.invalid_password_error_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setOnClickListener() {
        btnSignUp.setOnClickListener {
            presenter.onSignUpClicked(
                etSignUpEmail.text.toString(),
                etSignUpUsername.text.toString(),
                etSignUpPassword.text.toString(),
                etSignUpConfirmPassword.text.toString()
            )
        }
        btnGoToLogin.setOnClickListener {
            openLoginActivity()
        }
    }
}