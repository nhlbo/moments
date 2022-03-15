package com.example.moments.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.login.LoginActivityView
import javax.inject.Inject

class StartActivityView : BaseActivity(), IStartActivityView {

    @Inject
    lateinit var presenter: IStartActivityPresenter<IStartActivityView, IStartActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        presenter.onAttach(this)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun openMainActivity() {
        Log.d("Main_Activity", "Open Main Activity!!!!!")
    }

    override fun openLoginActivity() {
        val i: Intent = Intent(this, LoginActivityView::class.java)
        startActivity(i)
        finish()
    }
}