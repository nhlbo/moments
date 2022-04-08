package com.example.moments.ui.main.chat

import android.os.Bundle
import com.example.moments.R
import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.new_message.NewMessageActivityView
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

    private val adapter = GroupieAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setSupportActionBar(tbChatActivity)
        supportActionBar?.title = null
        tbChatActivity.setNavigationOnClickListener { finish() }
        val user = intent.getParcelableExtra<User>(NewMessageActivityView.USER_KEY)
        tvUsernameToolbarChat.text = user?.username

        rvChat.adapter = adapter
//        fetchChat()

        btnSendChat.setOnClickListener {
            val toId = user!!.id
            presenter.onPerformSendMessage(
                Message(
                    "",
                    etMessageBox.text.toString(),
                    presenter.getCurrentUserId(),
                    toId,
                    System.currentTimeMillis() / 1000
                )
            )
            adapter.add(ChatRightItem(etMessageBox.text.toString()))
            etMessageBox.text = null
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun fetchChat(users: List<User>) {
        adapter.add(ChatLeftItem("from message"))
        adapter.add(ChatLeftItem("from message"))
        adapter.add(ChatRightItem("to message"))
    }

    override fun addMessage(message: Message) {

    }
}

class ChatLeftItem(val text: String) :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvChatRight.text = text
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