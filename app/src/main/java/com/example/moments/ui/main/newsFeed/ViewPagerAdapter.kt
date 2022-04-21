package com.example.moments.ui.main.newsFeed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.moments.R

class ViewPagerAdapter() : PagerAdapter() {
    var context: Context? = null
    lateinit var imageList : List<String>
    var layoutInflater : LayoutInflater? = null
    constructor(images: List<String>, context: Context) : this() {
        this.imageList = images
        this.context = context
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }
    override fun getCount(): Int {
        return imageList.size
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == (`object` as LinearLayout)
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater?.inflate(R.layout.image_item, container, false)
        val imageView = itemView?.findViewById<ImageView>(R.id.imageView)
        context?.let {
            if (imageView != null) {
                Glide.with(it).load(imageList[position]).into(imageView)
            }
        }
        container.addView(itemView)
        return itemView!!
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}