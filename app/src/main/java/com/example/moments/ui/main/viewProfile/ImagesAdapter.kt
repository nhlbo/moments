package com.example.moments.ui.main.viewProfile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener

class ImagesAdapter (var context: Context, private val imagesList : ArrayList<String>, private val itemTouchListener: IOnRecyclerViewItemTouchListener?) :
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
        holder.img.setOnClickListener { itemTouchListener?.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun updateItems(newList: List<String>){
        val latestIndex = imagesList.size - 1
        imagesList.addAll(newList)
        notifyItemRangeChanged(latestIndex,newList.size)
    }

    fun replaceAllItems(newList: List<String>){
        imagesList.clear()
        imagesList.addAll(newList)
        notifyItemRangeInserted(0, newList.size)
    }
}