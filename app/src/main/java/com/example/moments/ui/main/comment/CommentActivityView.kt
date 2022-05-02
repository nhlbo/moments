package com.example.moments.ui.main.comment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListView
import androidx.appcompat.widget.Toolbar
import com.example.moments.R
import com.example.moments.data.model.RetrieviedRootComment
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.base.IBaseView
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

    private fun onPostButtonClicked(position: Int) {
        val postButton = findViewById<Button>(R.id.btnPostComment)

        postButton?.setOnClickListener {
            val newComment = CommentData(
                "2",
                "asd",
                "",
                listParent[position].commentId,
                commentBox?.text.toString(),
                0,
                "0"
            )
            listParent[position].replies.add(newComment)
            hashListChildren[listParent[position].commentId] = listParent[position].replies
            expandableListViewAdapter?.notifyDataSetChanged()

            commentBox?.setText("")
            commentBox?.clearFocus()
            commentBox?.closeSoftKeyboard()
        }
    }

    private fun onPostButtonClicked() {
        val postButton = findViewById<Button>(R.id.btnPostComment)

        postButton?.setOnClickListener {
            val newComment = CommentDataGroup(
                "2",
                "asd",/*new comment id*/
                "",
                "5",
                commentBox?.text.toString(),
                0,
                "0",
                arrayListOf()
            )
            listParent.add(newComment)
            hashListChildren[newComment.commentId] = newComment.replies
            expandableListViewAdapter?.notifyDataSetChanged()

            commentBox?.setText("")
            commentBox?.clearFocus()
            commentBox?.closeSoftKeyboard()
        }
    }

    private lateinit var listParent: MutableList<CommentDataGroup>
    private lateinit var hashListChildren: HashMap<String, List<CommentData>>
    private fun prepareListParent() {
        listParent = arrayListOf()
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())

        hashListChildren = HashMap()
        hashListChildren[listParent[0].commentId] = listParent[0].replies
        hashListChildren[listParent[1].commentId] = listParent[1].replies
        hashListChildren[listParent[2].commentId] = listParent[2].replies
        hashListChildren[listParent[3].commentId] = listParent[3].replies
        hashListChildren[listParent[4].commentId] = listParent[4].replies
        hashListChildren[listParent[5].commentId] = listParent[5].replies
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

    override fun updatePost(input : List<RetrieviedRootComment>) {
        for(item in input){
            listParent.add(CommentDataGroup.parseRetrieveRootComment(item))
        }
    }

    override fun updateComment(postId: String, content: String) {
        TODO("Not yet implemented")
    }
}