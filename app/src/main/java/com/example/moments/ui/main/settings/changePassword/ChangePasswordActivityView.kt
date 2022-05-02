package com.example.moments.ui.main.settings.changePassword

import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_change_password.*
import javax.inject.Inject

class ChangePasswordActivityView: BaseActivity(), IChangePasswordView {

    @Inject
    lateinit var presenter: IChangePasswordPresenter<IChangePasswordView, IChangePasswordInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        presenter.onAttach(this)
        tbChangePasswordActivity.setNavigationOnClickListener { finish() }
        setOnClickListener()
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onChangePasswordSuccess() {
        finish()
    }

    private fun setOnClickListener(){
        btnSubmitChangePassword.setOnClickListener {
            presenter.onSubmitChangePassword(
                etOldPassword.text.toString(),
                etNewPassword.text.toString(),
                etConfirmChangePassword.text.toString()
            )
        }
    }
}