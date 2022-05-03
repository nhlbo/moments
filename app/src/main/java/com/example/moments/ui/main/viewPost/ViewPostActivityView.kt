package com.example.moments.ui.main.viewPost

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.data.model.Post
import com.example.moments.data.model.RetrievedPost
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.comment.CommentData
import com.example.moments.ui.main.comment.CommentDataGroup
import com.example.moments.ui.main.comment.CommentsButtonClickListener
import com.example.moments.ui.main.comment.CustomExpandableListViewAdapter
import com.example.moments.ui.main.newsFeed.MediaSlidingAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class ViewPostActivityView : BaseActivity(), IViewPostView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)

        initLayouts()
    }
    @Inject
    lateinit var presenter: IViewPostPresenter<IViewPostView, IViewPostInteractor>

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun updatePost(post: RetrievedPost) {
        TODO("Not yet implemented")
    }


    private lateinit var commentBox: EditText
    private lateinit var expandableListViewAdapter: CustomExpandableListViewAdapter
    private lateinit var expandableListView: ExpandableListView

    private fun initLayouts(){
        commentBox = findViewById(R.id.etCommentBox)
        commentBox.setOnFocusChangeListener { _, _ ->
            if(commentBox.text.isNotEmpty()) return@setOnFocusChangeListener // reply to someone not a standard comment
            onPostButtonClicked()
        }

        prepareListParent()
        expandableListView = findViewById(R.id.elv_comment_viewPost)
        expandableListViewAdapter = initAdapter()
        expandableListView.setAdapter(expandableListViewAdapter)

        interactionButtonsClicked()
    }

    private fun initPostHeader(){
        val avatar = findViewById<ImageView>(R.id.ivAvatarHeaderPost)
        val username = findViewById<TextView>(R.id.tvUsername)
        val address = findViewById<TextView>(R.id.tvAddress)
    }

    private fun initPostInfo(post:Post){
        val likeCount = findViewById<TextView>(R.id.tvOnePostLikeCount)
        val caption = findViewById<TextView>(R.id.tvOnePostCaption)
        val dayCreated = findViewById<TextView>(R.id.tvOnePostCreated)
        
        val imagePager = findViewById<ViewPager2>(R.id.vpViewOnePost)
        val tabLayout = findViewById<TabLayout>(R.id.indicatorNewsFeed)
        imagePager.adapter = MediaSlidingAdapter(post.listMedia, this)
        imagePager.offscreenPageLimit = 3
        TabLayoutMediator(tabLayout, imagePager)
        { _, _ -> }.attach()
    }

    private fun initAdapter(): CustomExpandableListViewAdapter =
        CustomExpandableListViewAdapter(
            this,
            hashListChildren,
            listParent,
            object : CommentsButtonClickListener {
                override fun onReplyClicked(username: String, position: Int) {
                    commentBox.setText("@${username} ")
                    commentBox.requestFocus()
                    commentBox.showSoftKeyboard()
                    commentBox.setSelection(username.length + 2)

                    onPostButtonClicked(position)
                }
            }
        )

    private fun EditText.showSoftKeyboard(){
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun EditText.closeSoftKeyboard(){
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
    }

    private var currentCommentPosition: Int = -1
    private fun onPostButtonClicked(position: Int) {
        val postButton = findViewById<Button>(R.id.btnPostComment)
        currentCommentPosition = position

        postButton?.setOnClickListener {
            if(commentBox.text!!.isEmpty()) return@setOnClickListener

           // presenter.onUploadReply(postId, listParent[position].commentId, commentBox?.text.toString())
        }
    }

    private fun onPostButtonClicked() {
        val postButton = findViewById<Button>(R.id.btnPostComment)

        postButton?.setOnClickListener {
            if(commentBox?.text!!.isEmpty()) return@setOnClickListener

            //presenter.onUploadComment(postId, commentBox?.text.toString())
        }
    }


    private lateinit var listParent: MutableList<CommentDataGroup>
    private lateinit var hashListChildren: HashMap<String, MutableList<CommentData>>
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

    private fun interactionButtonsClicked(){
        val likeBtn = findViewById<ToggleButton>(R.id.btnLike)
        val commentBtn = findViewById<ImageButton>(R.id.btnComment)
        val shareBtn = findViewById<ImageButton>(R.id.btnShare)
        val saveBtn = findViewById<ToggleButton>(R.id.btnSave)

        likeBtn.setOnClickListener { /*TODO notify server post has been liked*/ }
        commentBtn.setOnClickListener{ commentBox.requestFocus() }
        shareBtn.setOnClickListener{/*TODO share post*/}
        saveBtn.setOnClickListener{/*TODO save post*/}
    }
}