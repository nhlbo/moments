package com.example.moments.ui.main.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.moments.R

class CommentFragmentView : Fragment() {
    private var toolBar: Toolbar? = null
    private var expandableListView: ExpandableListView? = null
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
        toolBar?.setNavigationOnClickListener {
            //TODO return newsfeed
        }
        onItemSelected()
        prepareListParent()
        expandableListView = getView()?.findViewById(R.id.elv_comment_post) as ExpandableListView
        expandableListView?.setAdapter(CustomExpandableListViewAdapter(requireContext(), hashListChildren, listParent))
    }

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
            activity?.supportFragmentManager?.popBackStack()
        }
    }
    private lateinit var listParent: ArrayList<CommentDataGroup>
    private lateinit var hashListChildren: HashMap<Int,List<CommentData>>
    private fun prepareListParent(){
        listParent = arrayListOf()
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())
        listParent.add(generateRootData())

        hashListChildren = HashMap()
        hashListChildren[listParent[0].userId] = listParent[0].replies
        hashListChildren[listParent[1].userId] = listParent[1].replies
        hashListChildren[listParent[2].userId] = listParent[2].replies
        hashListChildren[listParent[3].userId] = listParent[3].replies
        hashListChildren[listParent[4].userId] = listParent[4].replies
        hashListChildren[listParent[5].userId] = listParent[5].replies
    }

    private fun generateRootData() : CommentDataGroup{
        return CommentDataGroup(
            rootUserId = 1,
            rootUsername = "lorem",
            rootCommentId = 1,
            rootContent = "how to lay data tu firebase",
            rootReactions = -1,
            rootTimeUpload = "0s",
            replies = prepareListChild()
        )
    }

    private fun prepareListChild() : List<CommentData>{
        val res = arrayListOf<CommentData>()
        res.add(generateChildData())
        res.add(generateChildData())
        res.add(generateChildData())
        return res
    }

    private fun generateChildData(): CommentData{
        return CommentData(
            userId = 1,
            username = "lorem",
            commentId = 1,
            content = "hoi master Son",
            reactions = Int.MAX_VALUE - 1,
            timeUpload = "0s"
        )
    }
}