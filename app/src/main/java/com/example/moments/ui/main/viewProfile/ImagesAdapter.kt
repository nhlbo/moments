package com.example.moments.ui.main.viewProfile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R

class ImagesAdapter (var context: Context, private val imagesList : List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val img: ImageView =listItemView.findViewById(R.id.ivImageProfile)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(imagesList[adapterPosition])}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.image_list_view, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img : String = imagesList[position]
        Glide.with(context).load(img).into(holder.img)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

}