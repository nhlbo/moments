package com.example.moments.ui.main.latestMessage

import android.content.Intent
import android.os.Bundle
import com.example.moments.R
import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.newMessage.NewMessageActivityView
import kotlinx.android.synthetic.main.activity_latest_message.*
import javax.inject.Inject


class LatestMessageActivityView : BaseActivity(), ILatestMessageActivityView {
    @Inject
    lateinit var presenter: ILatestMessageActivityPresenter<ILatestMessageActivityView, ILatestMessageActivityInteractor>

    companion object {
        const val USER_KEY = "USER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_message)

        presenter.onAttach(this)
        presenter.onQueryLatestMessage()

        tbLatestMessageActivity.setNavigationOnClickListener { finish() }
        tbLatestMessageActivity.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.btnChat) {
                val intent = Intent(this, NewMessageActivityView::class.java)
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

    override fun addLatestMessages(latestMessage: List<Pair<User, Message>>) {
    }
}