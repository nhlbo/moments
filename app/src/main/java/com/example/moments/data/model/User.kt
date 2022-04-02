package com.example.moments.data.model

data class User(
    var username: String = "",
    var email: String = "",
    var avatar: String = "",
    var followingCount: Int = 0,
    var followerCount: Int = 0
)
