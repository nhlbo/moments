package com.example.moments.data.model

import com.google.firebase.Timestamp

class RetrievedPost {

    var id: String = ""
    var creator: User
    var caption: String = ""
    var listMedia: ArrayList<String> = arrayListOf()
    var createdAt: Timestamp = Timestamp.now()
    var likeCount: Int = 0
    var commentCount: Int = 0

    constructor(post: Post, user: User) {
        this.id = post.id
        this.creator = user
        this.caption = post.caption
        this.listMedia = ArrayList(post.listMedia)
        this.createdAt = post.createdAt
        this.likeCount = post.likeCount
        this.commentCount = post.commentCount
    }
}
