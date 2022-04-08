package com.example.moments.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    @DocumentId
    val id: String = "",
    var text: String = "",
    var fromId: String = "",
    var toId: String = "",
    var timeStamp: Long
) : Parcelable
