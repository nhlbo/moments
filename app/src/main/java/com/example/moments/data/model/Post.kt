package com.example.moments.data.model

import com.google.firebase.Timestamp

data class Post(
    var caption: String = "",
    var listMedia: ArrayList<String> = arrayListOf(),
    var createdAt: Timestamp = Timestamp.now(),
    var likeCount: Int = 0,
    var commentCount: Int = 0
)
