package com.example.moments.ui.main.newsFeed.sharePost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener


class ShareUserAdapter (var context: Context, private val usersList : ArrayList<User>, private val itemTouchListener: IOnRecyclerViewItemTouchListener?) :
    RecyclerView.Adapter<ShareUserAdapter.ViewHolder>() {
    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val img: ImageView =listItemView.findViewById(R.id.ivAvatarUserShare)
        val username : TextView = listItemView.findViewById(R.id.tvUsernameShare)
        val sendBtn : Button = listItemView.findViewById(R.id.btnSendSharedPost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_user_share_post, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : User = usersList[position]
        Glide.with(context).load(user.avatar).into(holder.img)
        holder.username.setText(user.username)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}