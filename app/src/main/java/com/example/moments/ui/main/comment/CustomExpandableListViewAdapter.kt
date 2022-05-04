package com.example.moments.ui.main.comment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.moments.R
import java.lang.StringBuilder

class Converter{
    companion object{
        fun stringToLong(string: String):Long{
            val result = StringBuilder()
            for (ch in string){
                result.append(ch.digitToInt())
            }
            return result.toString().toLong()
        }
    }
}

class CustomExpandableListViewAdapter(private val context: Context,
                                      private val listChildData: HashMap<String, MutableList<CommentData>>,
                                      private val listParent: MutableList<CommentDataGroup>,
                                      private val onButtonClickListener: CommentsButtonClickListener) : BaseExpandableListAdapter()
{

    override fun getGroupCount(): Int = listParent.size

    override fun getChildrenCount(p0: Int): Int = listChildData[listParent[p0].commentId]!!.size

    override fun getGroup(p0: Int): Any = listParent[p0]

    override fun getChild(p0: Int, p1: Int): Any = listChildData[listParent[p0].commentId]!![p1]

    override fun getGroupId(p0: Int): Long = 0//Converter.stringToLong(listParent[p0].commentId)

    override fun getChildId(p0: Int, p1: Int): Long = 0//listChildData[listParent[p0].commentId]!![p1].userId.toLong()

    override fun hasStableIds(): Boolean = false

    override fun isChildSelectable(p0: Int, p1: Int): Boolean = true

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, p2: View?, parent: ViewGroup?): View {
        var convertView = p2
        if(p2 == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.component_post_comment_root, null)
        }

        //get data
        val commentData = getGroup(groupPosition) as CommentDataGroup

        //get views from layout
        val userAva = convertView!!.findViewById<ImageView>(R.id.ivAvatarPostCommentOther)
        val userCommentContent = convertView.findViewById<TextView>(R.id.tvDescriptionPostComment)
        val userCommentLikes = convertView.findViewById<TextView>(R.id.tvCommentLikeNumber)
        val replyBtn = convertView.findViewById<TextView>(R.id.tvReplyComment)
        val timeUpload = convertView.findViewById<TextView>(R.id.tvTimePostComment)
        val reactBtn = convertView.findViewById<ToggleButton>(R.id.toggleLikePostCommentBtn)

        //update UI
        // avatar
        Glide.with(convertView).load(commentData.avatar).into(userAva)

        // comment
        userCommentContent.setText(
            buildSpannableString(
                groupPosition,
                commentData.username,
                commentData.content,
                commentData.tagPeople
            ),
            TextView.BufferType.SPANNABLE)

        userCommentContent.movementMethod = LinkMovementMethod.getInstance()
        userCommentContent.highlightColor = Color.TRANSPARENT
        // likes
        userCommentLikes.text = "${commentData.reactions} likes"
        timeUpload.text = commentData.timeUpload + " ago"

        // show replies button behaviours
        val showReplies = convertView.findViewById<TextView>(R.id.view_more_comments_btn)
        if (commentData.replies.size == 0) showReplies.visibility = View.GONE
        if(isExpanded){
            showReplies.text = "hide replies"
        }
        else{
            showReplies.text = "show ${commentData.replies.size} replies"
        }
        showReplies.setOnClickListener {
            if(isExpanded){
                (parent as ExpandableListView).collapseGroup(groupPosition)
            }
            else{
                (parent as ExpandableListView).expandGroup(groupPosition)
            }
        }

        //like button in root comment
        reactBtn.setOnClickListener {
            //keep in mind that btn state changed after touching.
            if(!reactBtn.isChecked) commentData.reactions-- // current btn state is unchecked
            else commentData.reactions++ // is checked

            //update UI
            userCommentLikes.text = "${commentData.reactions} likes"
        }

        replyBtn.setOnClickListener{
            onButtonClickListener.onReplyClicked(commentData.username, groupPosition)
        }

        userAva.setOnClickListener { onButtonClickListener.onUserNameClicked(commentData.username, groupPosition) }

        return convertView
    }

    override fun getChildView(groupPosition: Int, p1: Int, isExpanded: Boolean, p3: View?, parent: ViewGroup?): View {
        var convertView = p3
        if(p3 == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.component_post_comment, null)
        }
        // due to this is a child -> indent to the right for clearer view
        convertView!!.setPadding(60,0,0,0)
        //get data
        val commentData = getChild(groupPosition, p1) as CommentData

        //set ui views
        val userAva = convertView.findViewById<ImageView>(R.id.ivAvatarPostCommentOther)
        val userCommentContent = convertView.findViewById<TextView>(R.id.tvDescriptionPostComment)
        val userCommentLikes = convertView.findViewById<TextView>(R.id.tvCommentLikeNumber)
        val replyBtn = convertView.findViewById<TextView>(R.id.tvReplyComment)
        val timeUpload = convertView.findViewById<TextView>(R.id.tvTimePostComment)
        val reactBtn = convertView.findViewById<ToggleButton>(R.id.toggleLikePostCommentBtn)

        //update UI
        // avatar
        Glide.with(convertView).load(commentData.avatar).into(userAva)
        //comment
        userCommentContent.setText(
            buildSpannableString(
                groupPosition,
                commentData.username,
                commentData.content,
                commentData.tagPeople
            ),
            TextView.BufferType.SPANNABLE)

        userCommentContent.movementMethod = LinkMovementMethod.getInstance()
        userCommentContent.highlightColor = Color.TRANSPARENT
        //like and date
        userCommentLikes.text = "${commentData.reactions} likes"
        timeUpload.text = commentData.timeUpload + " ago"

        //like button in child comment
        reactBtn.setOnClickListener {
            if(!reactBtn.isChecked) commentData.reactions--
            else commentData.reactions++

            userCommentLikes.text = "${commentData.reactions} likes"
        }

        replyBtn.setOnClickListener{
            onButtonClickListener.onReplyClicked(commentData.username, groupPosition)
        }

        userAva.setOnClickListener { onButtonClickListener.onUserNameClicked(commentData.username, groupPosition) }

        return convertView
    }

    @SuppressLint("ResourceType")
    private fun buildSpannableString(
        position: Int,
        username:String,
        content:String,
        tagPeople: ArrayList<String>
    ): Spannable
    {
        val fullContent = "$username $content"
        val ss : Spannable = SpannableString(fullContent)
        val usernameLink: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                onButtonClickListener.onUserNameClicked(username, position)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(usernameLink, 0, username.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(StyleSpan(Typeface.BOLD), 0, username.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(ForegroundColorSpan(context.getColor(R.color.bleu_de_france)), 0, username.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        var startIndex = 0
        for(tag in tagPeople){
            val userProfileLink: ClickableSpan = object : ClickableSpan(){
                override fun onClick(p0: View) {
                    onButtonClickListener.onUserNameClicked(tag.subSequence(1, tag.length).toString(), position)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }
            startIndex = Math.max(fullContent.indexOf(tag), startIndex + 1)// if tags are duplicated
            ss.setSpan(userProfileLink, startIndex, startIndex + tag.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            ss.setSpan(ForegroundColorSpan(Color.CYAN), startIndex, startIndex + tag.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return ss
    }
}