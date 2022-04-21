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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivityView : BaseActivity(), ILoginActivityView {
    @Inject
    lateinit var presenter: ILoginActivityPresenter<ILoginActivityView, ILoginActivityInteractor>

    lateinit var mGoogleSignInClient: GoogleSignInClient

    val REQ_CODE_GOOGLE_SIGN_IN = 1337

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.onAttach(this)
        setUpGoogle()
        setOnClickListener()
    }

    override fun onStart() {
        super.onStart()
        val lastSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (lastSignInAccount != null) {
            UpdateUI(lastSignInAccount)
        }
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

    private fun setUpGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("586030394871-c28o0b48bbiaunrp6nv1am0e4995qmn7.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    override fun openGoogleLoginActivity() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            showCustomToastMessage(e.localizedMessage)
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        presenter.onGoogleLoginProcess(credential)
    }
}