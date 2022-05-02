package com.example.moments.ui.main.comment

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.RetrieviedComment
import com.example.moments.data.model.RetrieviedRootComment
import com.example.moments.ui.base.BaseActivity
import io.reactivex.Single
import kotlinx.android.synthetic.main.component_post_description.*
import javax.inject.Inject


class CommentActivityView : BaseActivity(), ICommentActivityView {
    private var toolBar: Toolbar? = null
    private var expandableListView: ExpandableListView? = null
    private var expandableListViewAdapter: CustomExpandableListViewAdapter? = null
    private var commentBox: EditText? = null

    @Inject
    lateinit var presenter: ICommentActivityPresenter<ICommentActivityView, ICommentActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        presenter.onAttach(this)

        toolBar = findViewById(R.id.tbCommentHeader)
        onItemSelected()

        commentBox = findViewById(R.id.etCommentBox)
        commentBox?.setOnFocusChangeListener { _, _ ->
            if(commentBox?.text!!.isNotEmpty()) return@setOnFocusChangeListener // reply to someone not a standard comment
            onPostButtonClicked()
        }

        prepareListParent()
        expandableListView = findViewById(R.id.elv_comment_post)
        expandableListViewAdapter = initAdapter()
        expandableListView?.setAdapter(expandableListViewAdapter)

        postId = intent.extras!!["postId"] as String
        presenter.onPreparedView(postId)
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    private fun initAdapter(): CustomExpandableListViewAdapter =
        CustomExpandableListViewAdapter(
            this,
            hashListChildren,
            listParent,
            object : CommentsButtonClickListener {
                override fun onReplyClicked(username: String, position: Int) {
                    commentBox?.requestFocus()
                    commentBox?.showSoftKeyboard()
                    commentBox?.setText("@${username} ")
                    commentBox?.setSelection(username.length + 2)

                    onPostButtonClicked(position)
                }
            }
        )

    private fun onItemSelected() {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sharePostBtn -> {
                    // Navigate to settings screen
                    true
                }
                else -> false
            }
        }
        toolBar?.setNavigationOnClickListener { finish() }
    }

    private fun EditText.showSoftKeyboard() {
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun EditText.closeSoftKeyboard() {
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
    }

    private lateinit var listParent: MutableList<CommentDataGroup>
    private lateinit var hashListChildren: HashMap<String, MutableList<CommentData>>
    private fun prepareListParent() {
        listParent = arrayListOf()
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())

        hashListChildren = HashMap()
        hashListChildren[listParent[0].commentId] = listParent[0].replies
        hashListChildren[listParent[1].commentId] = listParent[1].replies
        hashListChildren[listParent[2].commentId] = listParent[2].replies
        hashListChildren[listParent[3].commentId] = listParent[3].replies
    }

    private fun generateRootData(): CommentDataGroup {
        return CommentDataGroup(
            rootUserId = "1",
            rootUsername = "lorem",
            rootUserAvatar = "",
            rootCommentId = "1",
            rootContent = "how to lay data tu firebase",
            rootReactions = -1,
            rootTimeUpload = "0s",
            replies = prepareListChild()
        )
    }

    private fun prepareListChild(): ArrayList<CommentData> {
        val res = arrayListOf<CommentData>()
        res.add(generateChildData())
        res.add(generateChildData())
        res.add(generateChildData())
        return res
    }

    private fun generateChildData(): CommentData {
        return CommentData(
            userId = "1",
            username = "lorem",
            avatar = "",
            commentId = "1",
            content = "hoi master Son",
            reactions = Int.MAX_VALUE - 1,
            timeUpload = "0s"
        )
    }

    override fun updatePostComment(input : List<RetrieviedRootComment>) {
        listParent.clear()
        for(i:Int in input.indices){
            listParent.add(CommentDataGroup.parseRetrieveRootComment(input[i]))
            hashListChildren[listParent[i].commentId] = listParent[i].replies
        }
        expandableListViewAdapter?.notifyDataSetChanged()
    }

    private var postId: String = ""
    override fun updatePost(input: RetrievedPost) {
        //postId = input.id
        Glide.with(this).load(input.creator.avatar).into(btnAvatarComment)
        tvCommentDescription.text = input.caption
        tvCommentTime.text = input.createdAt.toString()
    }

    private fun onPostButtonClicked() {
        val postButton = findViewById<Button>(R.id.btnPostComment)

        postButton?.setOnClickListener {
            if(commentBox?.text!!.isEmpty()) return@setOnClickListener

            presenter.onUploadComment(postId, commentBox?.text.toString())
        }
    }

    override fun updateComment(comment: RetrieviedRootComment) {
        commentBox?.setText("")
        commentBox?.clearFocus()
        commentBox?.closeSoftKeyboard()

        val commentDataGroup = CommentDataGroup.parseRetrieveRootComment(comment)
        listParent.add(commentDataGroup)
        hashListChildren[listParent[listParent.size - 1].commentId] = listParent[listParent.size - 1].replies
        expandableListViewAdapter?.notifyDataSetChanged()
    }

    private var currentCommentPosition: Int = -1
    private fun onPostButtonClicked(position: Int) {
        val postButton = findViewById<Button>(R.id.btnPostComment)
        currentCommentPosition = position

        postButton?.setOnClickListener {
            if(commentBox?.text!!.isEmpty()) return@setOnClickListener

            presenter.onUploadReply(postId, listParent[position].commentId, commentBox?.text.toString())
        }
    }

    override fun updateReply(reply: RetrieviedComment) {
        commentBox?.setText("")
        commentBox?.clearFocus()
        commentBox?.closeSoftKeyboard()

        val commentData = CommentData.parseRetrieveComment(reply)
        listParent[currentCommentPosition].replies.add(commentData)
        hashListChildren[listParent[currentCommentPosition].commentId] = listParent[currentCommentPosition].replies
        expandableListViewAdapter?.notifyDataSetChanged()
    }
}