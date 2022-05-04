package com.example.moments.ui.main.moments

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.example.moments.data.model.MomentsData
import com.mikhaellopez.circularimageview.CircularImageView
import java.lang.ref.WeakReference


class MomentsRecyclerViewAdapter(
    private val listData: ArrayList<MomentsData>,
    private val clickListener: MomentsButtonClickListener
) : RecyclerView.Adapter<MomentsRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(
        itemView: View,
        private val listener: WeakReference<MomentsButtonClickListener>
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val vvMoments = itemView.findViewById<VideoView>(R.id.vvMoments)
        val ivUserAvaMoments = itemView.findViewById<CircularImageView>(R.id.ivUserAvaMoments)
        val tvContentMoments = itemView.findViewById<TextView>(R.id.tvContentMoments)
        val tvAudioMoments = itemView.findViewById<TextView>(R.id.tvAudioMoments)
        val tvLikesMoments = itemView.findViewById<TextView>(R.id.tvLikesMoments)
        val btnLikeMoments = itemView.findViewById<ToggleButton>(R.id.btnLikeMoments)
        val btnCommentMoments = itemView.findViewById<ImageButton>(R.id.btnCommentMoments)
        val btnFollowMoments = itemView.findViewById<Button>(R.id.btnFollowMoments)
        val btnShareMoments = itemView.findViewById<ImageButton>(R.id.btnShareMoments)
        val btnMoreMoments = itemView.findViewById<ImageButton>(R.id.btnMoreMoments)

        override fun onClick(p0: View?) {
            if (p0 == null) return

            val option: ClickedButton?
            when (p0.id) {
                btnCommentMoments.id -> {
                    option = ClickedButton.COMMENT
                }
                else -> option = null
            }
            if (option == null) return
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

        val content = holder.tvContentMoments
        val audio = holder.tvAudioMoments
        val likes = holder.tvLikesMoments

        content.text = data.content
        audio.text = data.audio
        likes.text = data.likes.toString()
        val videoUri: Uri = Uri.parse(data.video)
        holder.vvMoments.setVideoURI(videoUri)
        holder.vvMoments.pause()
        holder.vvMoments.setOnPreparedListener {
            it.isLooping = true
            it.start()
        }
        onButtonClicked(holder)
    }

    override fun getItemCount(): Int = listData.size

    private fun onButtonClicked(holder: ViewHolder) {
        val ava = holder.ivUserAvaMoments
        val likeBtn = holder.btnLikeMoments
        val commentBtn = holder.btnCommentMoments
        val followBtn = holder.btnFollowMoments
        val shareBtn = holder.btnShareMoments
        val moreBtn = holder.btnMoreMoments

        ava.setOnClickListener(holder)
        commentBtn.setOnClickListener(holder)
        likeBtn.setOnClickListener(holder)
        followBtn.setOnClickListener(holder)
        shareBtn.setOnClickListener(holder)
        moreBtn.setOnClickListener(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.vvMoments.stopPlayback()
    }
}