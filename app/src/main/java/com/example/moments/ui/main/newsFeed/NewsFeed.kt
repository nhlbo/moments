package com.example.moments.ui.main.newsFeed

data class NewsFeed(
val name: String,
val avatar: String,
val address: String,
val content: String,
val imagesList: List<String>,
val likeNumber: Int,
val myAvatar: String,
val time: String
)
