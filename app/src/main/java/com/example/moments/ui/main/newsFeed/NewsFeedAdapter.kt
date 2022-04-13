package com.example.moments.ui.main.newsFeed

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moments.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class NewsFeedAdapter(var context: Context, private val newsFeedList: List<NewsFeed>, callback: IAdapterCallBack) :
    RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>() {

    private val adapterCallBack: IAdapterCallBack = callback

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val headerPost = listItemView.findViewById<View>(R.id.loHeaderNewsFeed)
        val avatar = headerPost.findViewById<ImageView>(R.id.ivAvatarHeaderPost)
        val name = headerPost.findViewById<TextView>(R.id.tvNameHeaderPost)
        val add = headerPost.findViewById<TextView>(R.id.tvAddress)

        var viewPager = listItemView.findViewById<ViewPager2>(R.id.vpNewsFeed)
        val linearLayout = listItemView.findViewById<TabLayout>(R.id.indicatorNewsFeed)


        val componentButton =
            listItemView.findViewById<View>(R.id.llComponentInteractionButtonNewsFeed)
        val btnComment = componentButton.findViewById<ImageButton>(R.id.btnComment)
        val btnLike = componentButton.findViewById<ToggleButton>(R.id.btnLike)
        val btnShare = componentButton.findViewById<ImageButton>(R.id.btnShare)
        val btnSave = componentButton.findViewById<ToggleButton>(R.id.btnSave)

        val like = listItemView.findViewById<TextView>(R.id.tvLikeNewsFeed)
        val content = listItemView.findViewById<TextView>(R.id.tvContentNewsFeed)
        val showAllComment = listItemView.findViewById<TextView>(R.id.tvShowAllComment)

        val footerPost = listItemView.findViewById<View>(R.id.loFooterPostCommentNewsFeed)
        val myAvatar = footerPost.findViewById<ImageView>(R.id.ivAvatarPostCommentSelf)
        val etCommentBox = footerPost.findViewById<EditText>(R.id.etCommentBox)
        val btnPostComment = footerPost.findViewById<Button>(R.id.btnPostComment)

        val time = listItemView.findViewById<TextView>(R.id.tvTimeNewsFeed)

    }

    override fun getItemCount(): Int {
        return newsFeedList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.component_post, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp: NewsFeed = newsFeedList[position]

        val viewPagerAdapter = MediaSlidingAdapter(tmp.imagesList, context)
        holder.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(holder.linearLayout, holder.viewPager)
        { _, _ ->}.attach()

        Glide.with(context).load(tmp.avatar).into(holder.avatar)
        Glide.with(context).load(tmp.myAvatar).into(holder.myAvatar)
        holder.like.text = "${tmp.likeNumber} like"
        holder.content.text = tmp.content
        holder.add.text = tmp.address
        holder.time.text = tmp.time
        holder.name.text = tmp.name

//        val dotscount = viewPagerAdapter.itemCount
//        val dots = arrayOfNulls<ImageView>(dotscount)
//
//        for (i in 0 until dotscount) {
//            dots[i] = ImageView(context)
//            dots[i]?.setImageDrawable(
//                ContextCompat.getDrawable(
//                    context,
//                    R.drawable.non_active_dot
//                )
//            )
//            val params = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            params.setMargins(8, 0, 8, 0)
//            holder.linearLayout.addView(dots[i], params)
//        }
//
//        dots.get(0)?.setImageDrawable(
//            ContextCompat.getDrawable(
//                context,
//                R.drawable.active_dot
//            )
//        )

        holder.showAllComment.setOnClickListener {
            adapterCallBack.onItemTouch(position, "showComment")
        }
        holder.btnComment.setOnClickListener{
            adapterCallBack.onItemTouch(position, "showComment")
        }

    }
}

