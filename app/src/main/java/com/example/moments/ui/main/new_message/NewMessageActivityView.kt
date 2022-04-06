package com.example.moments.ui.main.new_message

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.chat.ChatActivityView
import com.google.firebase.firestore.DocumentSnapshot
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import javax.inject.Inject


class NewMessageActivityView : BaseActivity(), INewMessageActivityView {
    @Inject
    lateinit var presenter: INewMessageActivityPresenter<INewMessageActivityView, INewMessageActivityInteractor>

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
            startActivity(intent)
            finish()
        }
        rvNewMessage.adapter = adapter
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun addUsers(users: List<DocumentSnapshot>) {
        for (user in users) {
            adapter.add(
                UserItemNewMessage(
                    user.id,
                    user.data?.get("fullname") as String,
                    user.data?.get("username") as String
                )
            )
        }
    }
}

class UserItemNewMessage(val userId: String, val fullname: String, val username: String) :
    Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvFullNameNewMessage.text = fullname
        viewHolder.itemView.tvUsernameNewMessage.text = username
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}