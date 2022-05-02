package com.example.moments.ui.main.comment

import com.example.moments.data.model.RetrieviedComment
import com.example.moments.data.model.RetrieviedRootComment

class CommentDataGroup(
    rootUserId: String,
    rootUsername: String,
    rootUserAvatar: String,
    rootCommentId: String,
    rootContent: String,
    rootReactions: Int,
    rootTimeUpload: String,
    val replies: MutableList<CommentData>
) : CommentData(rootUserId, rootUsername, rootUserAvatar, rootCommentId, rootContent, rootReactions, rootTimeUpload) {
    companion object{
        fun parseRetrieveRootComment(input:RetrieviedRootComment) : CommentDataGroup =
            CommentDataGroup(
                rootUserId = input.creator.id,
                rootUsername = input.creator.username,
                rootUserAvatar = input.creator.avatar,
                rootCommentId = input.id,
                rootContent = input.content,
                rootReactions = input.likeCount,
                rootTimeUpload = input.createdAt.toString(),
                replies = parseRetrieveComments(input.replies)
            )

        private fun parseRetrieveComments(input: MutableList<RetrieviedComment>) : MutableList<CommentData>{
            val result = mutableListOf<CommentData>()
            input.forEach{ result.add(parseRetrieveComment(it)) }
            return result
        }
    }
}