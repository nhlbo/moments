package com.example.moments.ui.main.viewFollowList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R

data class Followers(val userId:String, val avatar: String, val name: String, val type: Boolean)

class FollowersAdapter(var context: Context, private val followersList: List<Followers>) :
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    var onItemClick: ((Followers) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val ava = listItemView.findViewById<ImageView>(R.id.ivAvatarFollowers)
        val avaName = listItemView.findViewById<TextView>(R.id.tvNameFollowers)
        val layout = listItemView.findViewById<LinearLayout>(R.id.llRowFollowers)

        init {
            listItemView.setOnClickListener { onItemClick?.invoke(followersList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.row_followers, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temp: Followers = followersList[position]
        Glide.with(context).load(temp.avatar).into(holder.ava)
        val textView = holder.avaName
        textView.setText(temp.name)
        if (temp.type == true) {
            val tmp = holder.layout
            tmp.isVisible = false
        }
    }

    override fun getItemCount(): Int {
        return followersList.size
    }

}