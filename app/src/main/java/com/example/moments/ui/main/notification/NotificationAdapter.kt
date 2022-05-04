package com.example.moments.ui.main.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.RetrievedNotification
import com.example.moments.databinding.RowCommonNotificationBinding
import com.example.moments.databinding.RowFollowNotificationBinding
import com.google.firebase.Timestamp
import java.text.DateFormat


sealed class NotificationRecyclerViewItem {

    class commonNotification(
        val avatar: String,
        val content: String,
        val time: String,
        val avatarOther: String
    ) : NotificationRecyclerViewItem()

    class followNotification(
        val name : String,
        val avatar: String,
        val content: String,
        val time: Timestamp,
    ) : NotificationRecyclerViewItem()

    companion object{
        fun parseRetrievedNotification(input : RetrievedNotification) : NotificationRecyclerViewItem{
            if(input.type == "")
                return commonNotification(
                    avatar = input.creator!!.avatar,
                    content = "", //TODO add post to this content
                    time = input.createdAt.toString(),
                    avatarOther = input.media
                )
            return followNotification(
                name = input.creator!!.username,
                avatar = input.creator!!.avatar,
                content = "", //TODO add post to this content
                time = input.createdAt,
            )
        }
    }
}

sealed class NotificationRecyclerViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var itemClickListener: ((view: View, item: NotificationRecyclerViewItem, position: Int) -> Unit)? = null
    var onButtonClick: ((view: View, item: NotificationRecyclerViewItem, position: Int) -> Unit)? = null
    class CommonViewHolder(
        private val binding: RowCommonNotificationBinding,
        val context: Context
    ) :
        NotificationRecyclerViewHolder(binding) {
        fun bind(common: NotificationRecyclerViewItem.commonNotification) {
            Glide.with(context).load(common.avatar).into(binding.ivAvatarCommonNotification)
            Glide.with(context).load(common.avatarOther)
                .into(binding.ivAvatarOthersCommonNotification)
            binding.tvContent.text = common.content
            binding.tvTimeNotification.text = common.time
            binding.root.setOnClickListener{
                itemClickListener?.invoke(it,common,adapterPosition)
            }
        }
    }

    class FollowViewHolder(
        private val binding: RowFollowNotificationBinding,
        val context: Context
    ) :
        NotificationRecyclerViewHolder(binding) {
        fun bind(follow: NotificationRecyclerViewItem.followNotification) {
            Glide.with(context).load(follow.avatar).into(binding.ivAvatarFollowNotification)
            binding.tvContentFollow.text = "${follow.name} likes your post"
            binding.tvTimeFollowNotification.text =  DateFormat.getDateInstance().format(follow.time.toDate())

            binding.root.setOnClickListener{
                itemClickListener?.invoke(it, follow, adapterPosition)
            }
        }
    }
}

class NotificationRecyclerViewAdapter(var context: Context) :
    RecyclerView.Adapter<NotificationRecyclerViewHolder>() {

    var items = mutableListOf<NotificationRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((view: View, item: NotificationRecyclerViewItem, position: Int) -> Unit)? = null
    var onButtonClick: ((view: View, item: NotificationRecyclerViewItem, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationRecyclerViewHolder {
        return when (viewType) {
            R.layout.row_common_notification -> NotificationRecyclerViewHolder.CommonViewHolder(
                RowCommonNotificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), context
            )
            R.layout.row_follow_notification -> NotificationRecyclerViewHolder.FollowViewHolder(
                RowFollowNotificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), context
            )

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: NotificationRecyclerViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener

        when (holder) {
            is NotificationRecyclerViewHolder.CommonViewHolder -> holder.bind(items[position] as NotificationRecyclerViewItem.commonNotification)
            is NotificationRecyclerViewHolder.FollowViewHolder -> {
                holder.bind(items[position] as NotificationRecyclerViewItem.followNotification)
                holder.onButtonClick = onButtonClick
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is NotificationRecyclerViewItem.commonNotification -> R.layout.row_common_notification
            is NotificationRecyclerViewItem.followNotification -> R.layout.row_follow_notification
        }
    }

    fun updateList(inputList: List<RetrievedNotification>){
        items.clear()
        for(input in inputList){
            items.add(NotificationRecyclerViewItem.parseRetrievedNotification(input))
        }
        notifyDataSetChanged()
    }
}