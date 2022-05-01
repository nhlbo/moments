package com.example.moments.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

open class RetrieviedComment(comment: Comment, user: User) {
    @DocumentId
    var id: String = comment.id
    var creator: User = user
    var content: String = comment.content
    var likeCount: Int = comment.likeCount
    var createdAt: Timestamp = comment.createdAt
}


class RetrieviedRootComment(comment: Comment, user: User) : RetrieviedComment(comment, user) {
    var replies: MutableList<RetrieviedComment> = mutableListOf()
}