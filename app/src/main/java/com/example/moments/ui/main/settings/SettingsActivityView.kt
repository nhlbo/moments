package com.example.moments.ui.main.settings

import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import javax.inject.Inject

class SettingsActivityView : BaseActivity(), ISettingsActivityView {
    @Inject
    lateinit var presenter: ISettingsActivityPresenter<ISettingsActivityView, ISettingsActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        presenter.onAttach(this)

    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}
