package com.example.moments.ui.forgetPassword.stepThree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.login.LoginActivityView
import kotlinx.android.synthetic.main.activity_forget_password_step_three.*
import javax.inject.Inject

class ForgetPasswordActivityStepThreeView : BaseActivity(), IForgetPasswordActivityStepThreeView {

    @Inject
    lateinit var presenter: IForgetPasswordActivityStepThreePresenter<IForgetPasswordActivityStepThreeView, IForgetPasswordActivityStepThreeInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_step_three)
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
        val intent: Intent = Intent(this, LoginActivityView::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun showValidationMessage(errorCode: Int) {
        TODO("Not yet implemented")
    }

    private fun setOnClickListener(){
        btnForgetPasswordStepThreeContinue.setOnClickListener {
            presenter.onForgetPasswordStepThreeContinueClicked(
                etForgetPasswordStepThreePassword.text.toString(),
                etForgetPasswordStepThreeConfirmPassword.text.toString()
            )
        }
    }
}