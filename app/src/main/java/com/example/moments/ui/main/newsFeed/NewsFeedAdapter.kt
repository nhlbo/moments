package com.example.moments.ui.main.newsFeed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.bumptech.glide.Glide
import com.example.moments.R


class NewsFeed(
    val name: String,
    val avatar: String,
    val address: String,
    val content: String,
    val imagesList: List<String>,
    val likeNumber: Int,
    val myAvatar: String,
    val time: String
)

class NewsFeedAdapter(var context: Context, private val newsFeedList: List<NewsFeed>) :
    RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>() {
    val dotscount : Int = 0
    val dots = arrayOfNulls<ImageView>(0)
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val headerPost = listItemView.findViewById<View>(R.id.loHeaderNewsFeed)
        val avatar = headerPost.findViewById<ImageView>(R.id.ivAvatarHeaderPost)
        val name = headerPost.findViewById<TextView>(R.id.tvNameHeaderPost)
        val add = headerPost.findViewById<TextView>(R.id.tvAddress)

        var viewPager = listItemView.findViewById<ViewPager>(R.id.vpNewsFeed)
        val linearLayout = listItemView.findViewById<LinearLayout>(R.id.llSliderDots)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.component_post, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp: NewsFeed = newsFeedList[position]

        val viewPagerAdapter = ViewPagerAdapter(tmp.imagesList, context)
        holder.viewPager.adapter = viewPagerAdapter

        Glide.with(context).load(tmp.avatar).into(holder.avatar)
        Glide.with(context).load(tmp.myAvatar).into(holder.myAvatar)
        holder.like.setText("${tmp.likeNumber} like")
        holder.content.setText(tmp.content)
        holder.add.setText(tmp.address)
        holder.time.setText(tmp.time)
        holder.name.setText(tmp.name)

        val dotscount = viewPagerAdapter.count
        val dots = arrayOfNulls<ImageView>(dotscount)

        for (i in 0 until dotscount) {
            dots[i] = ImageView(context)
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.non_active_dot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            holder.linearLayout.addView(dots.get(i), params)
        }

        dots.get(0)?.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.active_dot
            )
        )
        holder.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotscount) {
                    dots[i]!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            getApplicationContext(),
                            R.drawable.non_active_dot
                        )
                    )
                }
                dots[position]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.active_dot
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun getItemCount(): Int {
        return newsFeedList.size
    }
}

