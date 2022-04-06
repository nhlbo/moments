package com.example.moments.ui.main.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.main.new_message.NewMessageActivityView
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header_chat.*

class ChatActivityView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setSupportActionBar(tbChatActivity)
        supportActionBar?.title = null
        tbChatActivity.setNavigationOnClickListener { finish() }
        val user = intent.getParcelableExtra<User>(NewMessageActivityView.USER_KEY)
        tvUsernameToolbarChat.text = user?.username

        val adapter = GroupieAdapter()
        adapter.add(ChatLeftItem())
        adapter.add(ChatLeftItem())
        adapter.add(ChatRightItem())
        rvChat.adapter = adapter
    }
}

class ChatRightItem() :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    override fun getLayout(): Int {
        return R.layout.chat_right_row
    }
}

class ChatLeftItem() :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    override fun getLayout(): Int {
        return R.layout.chat_left_row
    }
}