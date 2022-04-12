package com.example.moments.ui.main.viewFollowList

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R


data class Following(val avatar: String, val name: String, val subName: String)

class FollowingAdapter(var context: Context, private val followingList: List<Following>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    var onItemClick: ((Following) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val ava = listItemView.findViewById<ImageView>(R.id.ivAvatarFollowing)
        val avaName = listItemView.findViewById<TextView>(R.id.tvNameFollowing)
        val avaSubName = listItemView.findViewById<TextView>(R.id.tvSubNameFollowing)
        val subMenu = listItemView.findViewById<TextView>(R.id.textViewOptions)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(followingList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.row_following, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temp: Following = followingList[position]
        Glide.with(context).load(temp.avatar).into(holder.ava)
        val textViewName = holder.avaName
        textViewName.setText(temp.name)
        val textViewSubName = holder.avaSubName
        textViewSubName.setText(temp.subName)

        holder.subMenu.setOnClickListener(View.OnClickListener { //creating a popup menu
            val popup = PopupMenu(context, holder.subMenu, Gravity.BOTTOM)
            popup.inflate(R.menu.option_following_menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menuManage -> {}
                    R.id.menuMute -> {}
                }
                false
            }
            popup.show()
        })
    }

    override fun getItemCount(): Int {
        return followingList.size
    }

}