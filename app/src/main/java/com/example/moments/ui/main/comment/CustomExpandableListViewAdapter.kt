package com.example.moments.ui.main.comment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.fonts.Font
import android.graphics.fonts.FontStyle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.example.moments.R
import org.intellij.lang.annotations.JdkConstants


class CustomExpandableListViewAdapter(private val context: Context,
                                      private val listChildData: HashMap<Int, List<CommentData>>,
                                      private val listParent: List<CommentDataGroup>) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = listParent.size

    override fun getChildrenCount(p0: Int): Int = listChildData[listParent[p0].userId]!!.size

    override fun getGroup(p0: Int): Any = listParent[p0]

    override fun getChild(p0: Int, p1: Int): Any = listChildData[listParent[p0].userId]!![p1]

    override fun getGroupId(p0: Int): Long = listParent[p0].userId.toLong()

    override fun getChildId(p0: Int, p1: Int): Long = listChildData[listParent[p0].userId]!![p1].userId.toLong()

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
        userCommentContent.text = buildSpannableString(commentData.username,commentData.content)
        userCommentContent.movementMethod = LinkMovementMethod.getInstance()
        userCommentContent.highlightColor = Color.TRANSPARENT

        userCommentLikes.text = "${commentData.reactions} likes"
        timeUpload.text = commentData.timeUpload + " ago"

        // show replies button behaviours
        val showReplies = convertView.findViewById<TextView>(R.id.view_more_comments_btn)
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
        userCommentContent.text = buildSpannableString(commentData.username,commentData.content)
        userCommentContent.movementMethod = LinkMovementMethod.getInstance()
        userCommentContent.highlightColor = Color.TRANSPARENT

        userCommentLikes.text = "${commentData.reactions} likes"
        timeUpload.text = commentData.timeUpload + " ago"

        //like button in child comment
        reactBtn.setOnClickListener {
            if(!reactBtn.isChecked) commentData.reactions--
            else commentData.reactions++

            userCommentLikes.text = "${commentData.reactions} likes"
        }

        return convertView
    }

    private fun buildSpannableString(username:String, content:String): String{
        val ss = SpannableString("$username $content")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(clickableSpan, 0, username.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ss.toString()
    }
}