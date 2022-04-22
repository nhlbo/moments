package com.example.moments.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @DocumentId
    val id: String = "",
    var username: String = "",
    var email: String = "",
    var avatar: String = "https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4",
    var bio: String = "",
    var followingCount: Int = 0,
    var followerCount: Int = 0,
    var private: Boolean = false
) : Parcelable
