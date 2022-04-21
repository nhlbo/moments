package com.example.moments.ui.main.message

import android.content.Intent
import android.os.Bundle
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.newMessage.NewMessageActivityView
import kotlinx.android.synthetic.main.activity_message.*


class MessageActivityView : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        tbMessageActivity.setNavigationOnClickListener { finish() }
        tbMessageActivity.setOnMenuItemClickListener { item ->
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
}