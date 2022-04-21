package com.example.moments.ui.main.comment

class CommentDataGroup(
    rootUserId: Int,
    rootUsername: String,
    rootCommentId: Int,
    rootContent: String,
    rootReactions: Int,
    rootTimeUpload: String,
    val replies: ArrayList<CommentData>
) : CommentData(rootUserId, rootUsername, rootCommentId, rootContent, rootReactions, rootTimeUpload) {

}