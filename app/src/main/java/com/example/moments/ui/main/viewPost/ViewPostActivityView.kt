package com.example.moments.ui.main.viewPost

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.RetrieviedComment
import com.example.moments.data.model.RetrieviedRootComment
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.comment.CommentData
import com.example.moments.ui.main.comment.CommentDataGroup
import com.example.moments.ui.main.comment.CommentsButtonClickListener
import com.example.moments.ui.main.comment.CustomExpandableListViewAdapter
import com.example.moments.ui.main.newsFeed.MediaSlidingAdapter
import com.example.moments.ui.main.viewOtherProfile.OtherProfileActivityView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_view_post.*
import kotlinx.android.synthetic.main.header_post.*
import java.text.DateFormat
import javax.inject.Inject

class ViewPostActivityView : BaseActivity(), IViewPostView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)
        presenter.onAttach(this)
        initLayouts()

        val postId = intent?.extras!!["postId"] as String
        presenter.onViewPrepared(postId)
    }
    @Inject
    lateinit var presenter: IViewPostPresenter<IViewPostView, IViewPostInteractor>

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    private lateinit var post: RetrievedPost
    override fun updatePost(post: RetrievedPost) {
        this.post = post
        Glide.with(this).load(post.creator.avatar).into(ivAvatarHeaderPost)
        ivAvatarHeaderPost.setOnClickListener{startViewProfileActivity(post.creator.id) }
        tvUsername.text = post.creator.username
        tvUsername.setOnClickListener { startViewProfileActivity(post.creator.id) }
        tvOnePostLikeCount.text = "${post.likeCount} likes"
        tvOnePostCaption.text = post.caption
        tvOnePostCreated.text = DateFormat.getDateInstance().format(post.createdAt.toDate())

        val imagePager = findViewById<ViewPager2>(R.id.vpViewOnePost)
        val tabLayout = findViewById<TabLayout>(R.id.indicatorNewsFeed)
        imagePager.adapter = MediaSlidingAdapter(post.listMedia, this)
        imagePager.offscreenPageLimit = 3
        TabLayoutMediator(tabLayout, imagePager)
        { _, _ -> }.attach()

        interactionButtonsClicked()
    }

    private val listener = object : CommentsButtonClickListener {
        override fun onReplyClicked(username: String, position: Int) {
            commentBox.setText("@${username} ")
            commentBox.requestFocus()
            commentBox.showSoftKeyboard()
            commentBox.setSelection(username.length + 2)

            onPostButtonClicked(position)
        }

        override fun onUserNameClicked(username: String, position: Int) {
            val userId: String =
                if(listParent[position].username == username){
                    //assign
                    listParent[position].userId
                } else{
                    val userReply = listParent[position].replies.find { x -> x.username == username } ?: return // if null -> return
                    //assign
                    userReply.userId
                }
            startViewProfileActivity(userId)
        }
    }
    override fun updatePostComment(input: List<RetrieviedRootComment>) {
        listParent.clear()
        hashListChildren.clear()
        for(i:Int in input.indices){
            listParent.add(CommentDataGroup.parseRetrieveRootComment(input[i]))
            hashListChildren[listParent[i].commentId] = listParent[i].replies
        }
        expandableListViewAdapter = initAdapter()
        expandableListView.setAdapter(expandableListViewAdapter)
    }

    override fun updateComment(comment: RetrieviedRootComment) {
        commentBox.setText("")
        commentBox.clearFocus()
        commentBox.closeSoftKeyboard()

        val commentDataGroup = CommentDataGroup.parseRetrieveRootComment(comment)
        listParent.add(commentDataGroup)
        hashListChildren[listParent[listParent.size - 1].commentId] = listParent[listParent.size - 1].replies
        expandableListViewAdapter.notifyDataSetChanged()
    }

    override fun updateReply(reply: RetrieviedComment) {
        commentBox.setText("")
        commentBox.clearFocus()
        commentBox.closeSoftKeyboard()

        val commentData = CommentData.parseRetrieveComment(reply)
        listParent[currentCommentPosition].replies.add(commentData)
        hashListChildren[listParent[currentCommentPosition].commentId] = listParent[currentCommentPosition].replies
        expandableListViewAdapter.notifyDataSetChanged()
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


    }

    private fun initAdapter(): CustomExpandableListViewAdapter =
        CustomExpandableListViewAdapter(
            this,
            hashListChildren,
            listParent,
            listener
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

            presenter.onUploadReply(post.id, listParent[position].commentId, commentBox.text.toString())
        }
    }

    private fun onPostButtonClicked() {
        val postButton = findViewById<Button>(R.id.btnPostComment)

        postButton?.setOnClickListener {
            if(commentBox.text!!.isEmpty()) return@setOnClickListener

            presenter.onUploadComment(post.id, commentBox.text.toString())
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
        tb_viewPost.setNavigationOnClickListener { finish() }
        val likeBtn = findViewById<ToggleButton>(R.id.btnLike)
        val commentBtn = findViewById<ImageButton>(R.id.btnComment)
        val saveBtn = findViewById<ToggleButton>(R.id.btnSave)

        likeBtn.isChecked = post.liked
        likeBtn.setOnClickListener {
            if(post.liked){
                post.likeCount--
                presenter.onUnlikePost(post.id)
            }else{
                post.likeCount++
                presenter.onLikePost(post.id)
            }
            post.liked = !post.liked
            tvOnePostLikeCount.text = "${post.likeCount} likes"
        }
        commentBtn.setOnClickListener{ commentBox.requestFocus() }

        saveBtn.isChecked = post.bookmarked
        saveBtn.setOnClickListener{
            if(post.bookmarked){
                presenter.onUnBookmarkPost(post.id)
            }else{
                presenter.onBookmarkPost(post.id)
            }
        }
    }

    private fun startViewProfileActivity(userId:String){
        val intent = Intent(this, OtherProfileActivityView::class.java)
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
    }
}