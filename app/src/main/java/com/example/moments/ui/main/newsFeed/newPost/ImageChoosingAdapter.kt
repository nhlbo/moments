package com.example.moments.ui.main.newsFeed.newPost

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener
import java.lang.Exception

class ImageChoosingAdapter(var context: Context, private val imagesList : ArrayList<String>, private val itemTouchListener: IOnRecyclerViewItemTouchListener?) :
    RecyclerView.Adapter<ImageChoosingAdapter.ViewHolder>() {
    var onItemClick: ((String) -> Unit)? = null
    var selectedItems: ArrayList<ImageView> = arrayListOf()
    var isMultipleSelect: Boolean = false

    class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val img: ImageView =listItemView.findViewById(R.id.ivImageChoosing)
        val toggleBtn: ToggleButton = listItemView.findViewById(R.id.toggleBtnSelectedItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.component_image_choosing, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img : String = imagesList[position]
        Glide.with(context).load(img).into(holder.img)

        if(isMultipleSelect){
            holder.toggleBtn.visibility = View.VISIBLE
        }
        else holder.toggleBtn.visibility = View.INVISIBLE

        holder.img.setOnClickListener {
            val index = selectedItems.indexOf(holder.img)
            val isExisted = index != -1
            holder.toggleBtn.isChecked = !isExisted
            if(isExisted){
                deselectItem(index)
            }
            else{
                selectItem(holder.img)
                holder.toggleBtn.text = ""+selectedItems.size
            }

            itemTouchListener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun updateItems(newList: List<String>){
        val latestIndex = imagesList.size - 1
        imagesList.addAll(newList)
        notifyItemRangeChanged(latestIndex,newList.size)
    }

    private fun deselectItem(position: Int){
        val imageView = selectedItems[position]
        imageView.foreground = ColorDrawable(Color.TRANSPARENT)
        selectedItems.removeAt(position)
    }

    private fun selectItem(imageView: ImageView){
        imageView.foreground = ColorDrawable(Color.argb(50,255,255,255))
        if(isMultipleSelect) selectedItems.add(imageView)
        else {
            try {
                selectedItems[0] = imageView
            }
            catch (ex:Exception){
                selectedItems.add(imageView)
            }
        }
    }

    fun setChosingType(value: Boolean){
        if(isMultipleSelect != value){
            isMultipleSelect = value
            this.notifyDataSetChanged()
        }
    }

}