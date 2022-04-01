package com.example.moments.ui.start

import android.content.Intent
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.login.LoginActivityView
import com.example.moments.ui.main.MainActivityView
import javax.inject.Inject

class StartActivityView : BaseActivity(), IStartActivityView {

    @Inject
    lateinit var presenter: IStartActivityPresenter<IStartActivityView, IStartActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        presenter.onAttach(this)
        presenter.test()
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

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun openMainActivity() {
        val intent: Intent = Intent(this, MainActivityView::class.java)
        startActivity(intent)
    }

    override fun openLoginActivity() {
        val i: Intent = Intent(this, LoginActivityView::class.java)
        startActivity(i)
        finish()
    }
}