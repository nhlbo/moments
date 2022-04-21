package com.example.moments.ui.main.chat

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.newMessage.NewMessageActivityView
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.chat_left_row.view.*
import kotlinx.android.synthetic.main.footer_chat_textbox.*
import kotlinx.android.synthetic.main.header_chat.*
import javax.inject.Inject

class ChatActivityView : BaseActivity(), IChatActivityView {
    @Inject
    lateinit var presenter: IChatActivityPresenter<IChatActivityView, IChatActivityInteractor>
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        presenter.onAttach(this)

        setSupportActionBar(tbSettingsActivity)
        supportActionBar?.title = null
        tbSettingsActivity.setNavigationOnClickListener { finish() }
        user = intent.getParcelableExtra<User>(NewMessageActivityView.USER_KEY)!!
        tvUsernameToolbarChat.text = user.username
        Glide.with(this).load(user.avatar).into(imgChatAvatar)

        presenter.onPerformListenToMessage()

        btnSendChat.setOnClickListener {
            val toId = user.id
            presenter.onPerformSendMessage(
                Message(
                    "",
                    etMessageBox.text.toString(),
                    presenter.getCurrentUserId(),
                    toId,
                    System.currentTimeMillis() / 1000
                )
            )
            etMessageBox.text = null
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun addMessages(messages: List<Message>) {
        val adapter = GroupieAdapter()
        for (message in messages) {
            if (message.fromId == presenter.getCurrentUserId()) {
                adapter.add(ChatRightItem(message.text))
            } else {
                adapter.add(ChatLeftItem(message.text, user))
            }
        }
        rvChat.adapter = adapter
    }
}

class ChatLeftItem(val text: String, val user: User) :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvChatRight.text = text
        Glide.with(viewHolder.itemView).load(user.avatar).into(viewHolder.itemView.imgAvatarChatLeft)
    }

    override fun getLayout(): Int {
        return R.layout.chat_left_row
    }
}

class ChatRightItem(val text: String) :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvChatRight.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_right_row
    }
}