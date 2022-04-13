package com.example.moments.ui.main.newsFeed

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MediaSlidingAdapter(private val imagesUri: List<String>, val context: Context) : RecyclerView.Adapter<MediaSlidingAdapter.MediaSlidingViewHolder>() {
    inner class MediaSlidingViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun setImageView(imagePath: String) {
            //custom settings for fast loading image
            Glide.with(context)
                .load(imagePath)
                .into(view as ImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaSlidingViewHolder {
        val imageView = ImageView(context)
        imageView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return MediaSlidingViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: MediaSlidingViewHolder, position: Int) {
        holder.setImageView(imagesUri[position])
    }

    override fun getItemCount(): Int = imagesUri.size
}