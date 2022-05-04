package com.example.moments.ui.main.savedPost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.Post
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener

class SavedPostAdapter (var context: Context, private val postsList : ArrayList<Post>, private val itemTouchListener: IOnRecyclerViewItemTouchListener?) :
    RecyclerView.Adapter<SavedPostAdapter.ViewHolder>() {
    var onItemClick: ((Post) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val img: ImageView =listItemView.findViewById(R.id.ivImageProfile)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(postsList[adapterPosition])}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.image_list_view, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp : Post = postsList[position]
        Glide.with(context).load(tmp.listMedia[0]).into(holder.img)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    fun updateItems(newList: List<Post>){
        val latestIndex = postsList.size - 1
        postsList.addAll(newList)
        notifyItemRangeChanged(latestIndex,newList.size)
    }

    fun replaceAllItems(newList: List<Post>){
        postsList.clear()
        postsList.addAll(newList)
        notifyDataSetChanged()
    }
}