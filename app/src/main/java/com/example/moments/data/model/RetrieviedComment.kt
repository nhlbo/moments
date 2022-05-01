package com.example.moments.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

open class RetrieviedComment(comment: Comment, user: User) {
    @DocumentId
    val id: String = comment.id
    val creator: User = user
    val content: String = comment.content
    val likeCount: Int = comment.likeCount
    val createdAt: Timestamp = comment.createdAt
}


class RetrieviedRootComment(comment: Comment, user: User) : RetrieviedComment(comment, user) {
    val replies: MutableList<Comment> = mutableListOf()
}