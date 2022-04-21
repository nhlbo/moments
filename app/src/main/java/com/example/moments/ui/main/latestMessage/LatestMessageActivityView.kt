package com.example.moments.ui.main.latestMessage

import android.content.Intent
import android.os.Bundle
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_message.*
import javax.inject.Inject


class LatestMessageActivityView : BaseActivity(), ILatestMessageActivityView {
    @Inject
    lateinit var presenter: ILatestMessageActivityPresenter<ILatestMessageActivityView, ILatestMessageActivityInteractor>

    companion object {
        const val USER_KEY = "USER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        tbMessageActivity.setNavigationOnClickListener { finish() }
        tbMessageActivity.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.btnChat) {
                val intent = Intent(this, LatestMessageActivityView::class.java)
                startActivity(intent)
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun addUsers(users: List<User>) {
        TODO("Not yet implemented")
    }
}