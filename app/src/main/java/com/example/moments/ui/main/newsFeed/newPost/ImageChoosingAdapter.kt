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

    var selectedItems: ArrayList<ImageView> = arrayListOf()

    private val maxCapacity = 10
    private var isMultipleSelect: Boolean = false
    private var currentSelectedItem: Int = -1
    private val orderItemSelection: HashMap<Int,Int> = HashMap()

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

        if(orderItemSelection.containsKey(position)){
            holder.toggleBtn.text = ""+orderItemSelection[position]
            holder.toggleBtn.isChecked = true
        }

        holder.img.setOnClickListener {
            onSelectItem(holder, position)
            itemTouchListener?.onItemClick(position)
        }
        holder.toggleBtn.setOnClickListener{
            onSelectItem(holder, position)
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

    private fun onSelectItem(holder: ViewHolder, position: Int){
        val index = selectedItems.indexOf(holder.img)
        val isExisted = index != -1

        if(isExisted && currentSelectedItem == position){
            deselectItem(index)
            holder.toggleBtn.isChecked = false
            holder.toggleBtn.text = ""

            //reset values in hashmap
            val value = orderItemSelection[position]!!
            orderItemSelection.remove(position)
            for(item in orderItemSelection){
                if(item.value < value) continue
                item.setValue(Integer.max(0, item.value - 1))
            }
            notifyItemRangeChanged(0, orderItemSelection.values.indexOf(selectedItems.size))
            return
        }
        if(maxCapacity > selectedItems.size && !isExisted){
            holder.toggleBtn.isChecked = true
            selectItem(holder.img)
            holder.toggleBtn.text = ""+selectedItems.size
            orderItemSelection[position] = selectedItems.size
        }
        currentSelectedItem = position
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
            resetItems()
            // show the toggle selector
            this.notifyDataSetChanged()
        }
    }

    private fun resetItems(){
        if(selectedItems.size == 0) return
        val firstItem : ImageView
        if(isMultipleSelect) {
            firstItem = selectedItems[currentSelectedItem]
        }
        else{
            firstItem = selectedItems[0]
            currentSelectedItem = 0
        }
        selectedItems.clear()
        selectedItems.add(firstItem)

        orderItemSelection.clear()
        orderItemSelection[currentSelectedItem] = 1
    }
}