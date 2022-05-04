package com.example.moments.data.model

import com.google.firebase.Timestamp

class RetrievedPost(post: Post, user: User) {
    var id: String = post.id
    var creator: User = user
    var caption: String = post.caption
    var listMedia: ArrayList<String> = ArrayList(post.listMedia)
    var createdAt: Timestamp = post.createdAt
    var likeCount: Int = post.likeCount
    var commentCount: Int = post.commentCount
    var liked: Boolean = false
    var bookmarked: Boolean = false
}
