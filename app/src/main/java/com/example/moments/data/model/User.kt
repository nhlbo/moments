package com.example.moments.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var username: String = "",
    var email: String = "",
    var fullname: String = "",
    var avatar: String = "",
    var followingCount: Int = 0,
    var followerCount: Int = 0,
    var private: Boolean = false
) : Parcelable
