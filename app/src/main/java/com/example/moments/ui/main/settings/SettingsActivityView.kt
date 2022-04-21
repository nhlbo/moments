package com.example.moments.ui.main.settings

import android.content.Intent
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.login.LoginActivityView
import com.example.moments.ui.main.settings.changePassword.ChangePasswordActivityView
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivityView : BaseActivity(), ISettingsActivityView {
    @Inject
    lateinit var presenter: ISettingsActivityPresenter<ISettingsActivityView, ISettingsActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        presenter.onAttach(this)
        tbEditProfileActivity.setNavigationOnClickListener { finish() }
        btnLogOut.setOnClickListener {
            presenter.onPerformLogOut()
            val intent: Intent = Intent(this, LoginActivityView::class.java)
            startActivity(intent)
            finishAffinity()
        }

        btnChangePassword.setOnClickListener {
            val intent: Intent = Intent(this, ChangePasswordActivityView::class.java)
            startActivity(intent)
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}
