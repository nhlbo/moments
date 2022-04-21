package com.example.moments.ui.main.moments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.mikhaellopez.circularimageview.CircularImageView
import java.lang.ref.WeakReference

class MomentsRecyclerViewAdapter(private val listData: ArrayList<MomentsData>,
                                 private val clickListener: MomentsButtonClickListener) : RecyclerView.Adapter<MomentsRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View,
                           private val listener: WeakReference<MomentsButtonClickListener>
                           ) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val avatarView = itemView.findViewById<CircularImageView>(R.id.ivUserAvaMoments)
        val contentView = itemView.findViewById<TextView>(R.id.tvContentMoments)
        val audioView = itemView.findViewById<TextView>(R.id.tvAudioMoments)
        val numberLikes = itemView.findViewById<TextView>(R.id.tvLikesMoments)
        val likeBtn = itemView.findViewById<ToggleButton>(R.id.btnLikeMoments)
        val commentBtn = itemView.findViewById<ImageButton>(R.id.btnCommentMoments)
        val followBtn = itemView.findViewById<Button>(R.id.btnFollowMoments)
        val shareBtn = itemView.findViewById<ImageButton>(R.id.btnShareMoments)
        val moreBtn = itemView.findViewById<ImageButton>(R.id.btnMoreMoments)

        override fun onClick(p0: View?)
        {
            if(p0 == null) return

            val option:ClickedButton?
            when(p0.id){
                commentBtn.id -> {
                    option = ClickedButton.COMMENT
                }
                else -> option = null
            }
            if(option == null) return
            listener.get()?.onPositionClicked(option, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.component_full_layout_moments, parent, false)
        return ViewHolder(view, WeakReference(clickListener))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]

        val content = holder.contentView
        val audio = holder.audioView
        val likes = holder.numberLikes

        content.text = data.content
        audio.text = data.audio
        likes.text = data.likes.toString()
        onButtonClicked(holder)
    }

    override fun getItemCount(): Int = listData.size

    private fun onButtonClicked(holder: ViewHolder){
        val ava = holder.avatarView
        val likeBtn = holder.likeBtn
        val commentBtn = holder.commentBtn
        val followBtn = holder.followBtn
        val shareBtn = holder.shareBtn
        val moreBtn = holder.moreBtn

        ava.setOnClickListener(holder)
        commentBtn.setOnClickListener(holder)
        likeBtn.setOnClickListener(holder)
        followBtn.setOnClickListener(holder)
        shareBtn.setOnClickListener(holder)
        moreBtn.setOnClickListener(holder)
    }
}