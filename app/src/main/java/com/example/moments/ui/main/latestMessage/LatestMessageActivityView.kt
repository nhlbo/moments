package com.example.moments.ui.main.latestMessage

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.Message
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.chat.ChatActivityView
import com.example.moments.ui.main.newMessage.NewMessageActivityView
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_latest_message.*
import kotlinx.android.synthetic.main.user_row_latest_message.view.*
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
        presenter.onListenToLatestMessage()

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

    override fun addLatestMessages(latestMessages: List<Pair<User, Message>>) {
        val adapter = GroupieAdapter()
        for ((user, message) in latestMessages) {
            adapter.add(MessageItemLatestMessage(user, message))
        }
        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(view.context, ChatActivityView::class.java)
            val userItem = item as MessageItemLatestMessage
            intent.putExtra(USER_KEY, userItem.user)
            startActivity(intent)
            finish()
        }
        rvLatestMessage.adapter = adapter
    }
}

class MessageItemLatestMessage(val user: User, val message: Message) :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvUserNameLatestMessage.text = user.username
        viewHolder.itemView.tvLatestMessage.text = message.text
        Glide.with(viewHolder.itemView).load(user.avatar).into(viewHolder.itemView.ivAvatarUserLatestMessage)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_latest_message
    }
}