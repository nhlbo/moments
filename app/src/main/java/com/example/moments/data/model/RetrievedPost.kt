package com.example.moments.data.model

import com.google.firebase.Timestamp

class RetrievedPost(post: Post, user: User) {

    var id: String = ""
    var creator: User = user
    var caption: String = ""
    var listMedia: ArrayList<String> = arrayListOf()
    var createdAt: Timestamp = Timestamp.now()
    var likeCount: Int = 0
    var commentCount: Int = 0

    init {
        this.id = post.id
        this.caption = post.caption
        this.listMedia = ArrayList(post.listMedia)
        this.createdAt = post.createdAt
        this.likeCount = post.likeCount
        this.commentCount = post.commentCount
    }
}
