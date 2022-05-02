package com.example.moments.ui.main.qrCode

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_qr_code.*
import javax.inject.Inject

class QRCodeActivityView : BaseActivity(), IQRCodeActivityView {

    @Inject
    lateinit var presenter: IQRCodeActivityPresenter<IQRCodeActivityView, IQRCodeActivityInteractor>

    companion object {
        const val DONE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        presenter.onAttach(this)
        initMediaGrid()
        tbQRCodeActivity.setNavigationOnClickListener { finish() }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    private fun initMediaGrid() {

        vp2QRCode?.adapter = QRCodeViewPagerAdapter(supportFragmentManager, lifecycle)
        vp2QRCode?.isUserInputEnabled = false
        TabLayoutMediator(tlQRCodeActivity, vp2QRCode!!) { tab, position ->
            when (position) {
                0 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_qr_code)
                1 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_camera)
            }
        }.attach()
    }
}


