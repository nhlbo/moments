package com.example.moments.ui.main.comment

open class CommentData(
    val userId: Int,
    val username: String,
    val commentId: Int,
    val content: String,
    var reactions: Int,
    val timeUpload: String
)