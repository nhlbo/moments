package com.example.moments.ui.main.newsFeed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.RetrievedPost
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.DateFormat

class NewsFeedAdapter(
    var context: Context,
    private var newsFeedList: MutableList<RetrievedPost>,
    callback: IAdapterCallBack
) :
    RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>() {

    var onButtonClick: ((RetrievedPost) -> Unit)? = null
    private val adapterCallBack: IAdapterCallBack = callback

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val headerPost = listItemView.findViewById<View>(R.id.postHeaderGroup)
        val headerAvatar = headerPost.findViewById<ImageView>(R.id.ivAvatarHeaderPost)
        val headerUsername = headerPost.findViewById<TextView>(R.id.tvUsername)
        val headerAddress = headerPost.findViewById<TextView>(R.id.tvAddress)

        var viewPager = listItemView.findViewById<ViewPager2>(R.id.vpNewsFeed)
        val linearLayout = listItemView.findViewById<TabLayout>(R.id.indicatorNewsFeed)


        val interactionBtnGroup =
            listItemView.findViewById<View>(R.id.interactionBtnGroup)
        val btnComment = interactionBtnGroup.findViewById<ImageButton>(R.id.btnComment)
        val btnLike = interactionBtnGroup.findViewById<ToggleButton>(R.id.btnLike)
        val btnShare = interactionBtnGroup.findViewById<ImageButton>(R.id.btnShare)
        val btnSave = interactionBtnGroup.findViewById<ToggleButton>(R.id.btnSave)

        val tvLikeCount = listItemView.findViewById<TextView>(R.id.tvLikeCount)
        val tvCaption = listItemView.findViewById<TextView>(R.id.tvCaption)
        val tvShowAllComment = listItemView.findViewById<TextView>(R.id.tvShowAllComment)

        val postCommentInputGroup = listItemView.findViewById<View>(R.id.postCommentInputGroup)
        val postCommentInputAvatar =
            postCommentInputGroup.findViewById<ImageView>(R.id.ivAvatarPostCommentSelf)
        val etPostCommentInput = postCommentInputGroup.findViewById<EditText>(R.id.etCommentBox)
        val btnPostCommentInput = postCommentInputGroup.findViewById<Button>(R.id.btnPostComment)

        val tvPostCreated = listItemView.findViewById<TextView>(R.id.tvPostCreated)

        init{
            tvShowAllComment.setOnClickListener{
                onButtonClick?.invoke(newsFeedList[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return newsFeedList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.component_post, parent, false)

        val params = contactView.layoutParams
        params.height = (parent.measuredHeight * 1.25f).toInt()
        contactView.layoutParams = params
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = newsFeedList[position]

        val viewPagerAdapter = MediaSlidingAdapter(tmp.listMedia, context)
        holder.viewPager.adapter = viewPagerAdapter
        holder.viewPager.offscreenPageLimit = 3
        TabLayoutMediator(holder.linearLayout, holder.viewPager)
        { _, _ -> }.attach()

        Glide.with(context).load(tmp.creator.avatar).into(holder.headerAvatar)
        Glide.with(context).load(tmp.creator.avatar).into(holder.postCommentInputAvatar)
        holder.tvLikeCount.text = "${tmp.likeCount} likes"
        holder.tvCaption.text = tmp.caption
//        holder.add.text = tmp.address
        holder.tvPostCreated.text = DateFormat.getDateInstance().format(tmp.createdAt.toDate())
        holder.headerUsername.text = tmp.creator.username


        holder.btnComment.setOnClickListener {
            adapterCallBack.onItemTouch(position, "showComment")
        }
        holder.headerAvatar.setOnClickListener { 
            adapterCallBack.onItemTouch(position, "viewProfile")
        }
        holder.headerUsername.setOnClickListener {
            adapterCallBack.onItemTouch(position, "viewProfile")
        }
    }

    fun updatePost(listPost: List<RetrievedPost>) {
        newsFeedList.clear()
        newsFeedList.addAll(listPost.toMutableList())
        notifyItemRangeChanged(0, newsFeedList.size)
    }

    fun prependPost(post: RetrievedPost) {
        newsFeedList.add(0, post)
        notifyItemInserted(0)
    }
}