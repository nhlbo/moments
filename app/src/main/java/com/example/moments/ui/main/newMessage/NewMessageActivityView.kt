package com.example.moments.ui.main.newMessage

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.chat.ChatActivityView
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import javax.inject.Inject


class NewMessageActivityView : BaseActivity(), INewMessageActivityView {
    @Inject
    lateinit var presenter: INewMessageActivityPresenter<INewMessageActivityView, INewMessageActivityInteractor>

    companion object {
        const val USER_KEY = "USER_KEY"
    }

    private val adapter = GroupieAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        presenter.onAttach(this)
        presenter.onQueryFollowingUser()

        setSupportActionBar(tbNewMessageActivity)
        tbNewMessageActivity.setNavigationOnClickListener { finish() }

        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(view.context, ChatActivityView::class.java)
            val userItem = item as UserItemNewMessage
            intent.putExtra(USER_KEY, userItem.user)
            startActivity(intent)
            finish()
        }
        rvNewMessage.adapter = adapter
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun addUsers(users: List<User>) {
        for (user in users) {
            adapter.add(UserItemNewMessage(user))
        }
    }
}

class UserItemNewMessage(val user: User) :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvFullNameNewMessage.text = user.email
        viewHolder.itemView.tvUsernameNewMessage.text = user.username
        Glide.with(viewHolder.itemView).load(user.avatar).into(viewHolder.itemView.ivAvatarUserNewMessage)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}