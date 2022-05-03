package com.example.moments.ui.main.comment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moments.R


class CommentFragmentView : Fragment() {
    private var toolBar: Toolbar? = null
    private var expandableListView: ExpandableListView? = null
    private var expandableListViewAdapter: CustomExpandableListViewAdapter? = null
    private var commentBox: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolBar = getView()?.findViewById(R.id.tbCommentHeader)
        onItemSelected()

        commentBox = getView()?.findViewById(R.id.etCommentBox)
        commentBox?.setOnFocusChangeListener { _, _ ->
            if(commentBox?.text!!.isNotEmpty()) return@setOnFocusChangeListener // reply to someone not a standard comment
            onPostButtonClicked()
        }

        prepareListParent()
        expandableListView = getView()?.findViewById(R.id.elv_comment_post) as ExpandableListView
        expandableListViewAdapter = initAdapter()
        expandableListView?.setAdapter(expandableListViewAdapter)
    }

    private fun initAdapter(): CustomExpandableListViewAdapter =
        CustomExpandableListViewAdapter(
            requireContext(),
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
        toolBar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun EditText.showSoftKeyboard(){
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun EditText.closeSoftKeyboard(){
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun onPostButtonClicked(position:Int){
        val postButton = view?.findViewById<Button>(R.id.btnPostComment)

        postButton?.setOnClickListener {
            val newComment = CommentData("2","asd","",listParent[position].commentId, commentBox?.text.toString(), 0,"0")
            listParent[position].replies.add(newComment)
            hashListChildren[listParent[position].commentId] = listParent[position].replies
            expandableListViewAdapter?.notifyDataSetChanged()

            commentBox?.setText("")
            commentBox?.clearFocus()
            commentBox?.closeSoftKeyboard()
        }
    }

    private fun onPostButtonClicked(){
        val postButton = view?.findViewById<Button>(R.id.btnPostComment)

        postButton?.setOnClickListener {
            val newComment = CommentDataGroup("2","asd","",/*new comment id*/"2", commentBox?.text.toString(), 0,"0", mutableListOf<CommentData>())
            listParent.add(newComment)
            hashListChildren[newComment.commentId] = newComment.replies
            expandableListViewAdapter?.notifyDataSetChanged()

            commentBox?.setText("")
            commentBox?.clearFocus()
            commentBox?.closeSoftKeyboard()
        }
    }

    private lateinit var listParent: ArrayList<CommentDataGroup>
    private lateinit var hashListChildren: HashMap<String,MutableList<CommentData>>
    private fun prepareListParent(){
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

    private fun generateRootData() : CommentDataGroup{
        return CommentDataGroup(
            rootUserId = "1",
            rootUsername = "lorem",
            rootUserAvatar = "lorem",
            rootCommentId = "1",
            rootContent = "how to lay data tu firebase",
            rootReactions = -1,
            rootTimeUpload = "0s",
            replies = mutableListOf()
        )
    }

//    private fun prepareListChild() : ArrayList<CommentData>{
//        val res = arrayListOf<CommentData>()
//        res.add(generateChildData())
//        res.add(generateChildData())
//        res.add(generateChildData())
//        return res
//    }

//    private fun generateChildData(): CommentData{
//        return CommentData(
//            userId = 1,
//            username = "lorem",
//            commentId = 1,
//            content = "hoi master Son",
//            reactions = Int.MAX_VALUE - 1,
//            timeUpload = "0s"
//        )
//    }
}