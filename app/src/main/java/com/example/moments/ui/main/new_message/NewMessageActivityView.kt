package com.example.moments.ui.main.message

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moments.R
import com.example.moments.ui.login.ILoginActivityInteractor
import com.example.moments.ui.login.ILoginActivityPresenter
import com.example.moments.ui.login.ILoginActivityView
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import javax.inject.Inject


class NewMessageActivityView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        setSupportActionBar(tbNewMessageActivity)
        tbNewMessageActivity.setNavigationOnClickListener { finish() }

        val adapter = GroupieAdapter()
//        adapter.add(UserItemNewMessage())
//        adapter.add(UserItemNewMessage())
//        adapter.add(UserItemNewMessage())
        rvNewMessage.adapter = adapter
    }
}

class UserItemNewMessage : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}