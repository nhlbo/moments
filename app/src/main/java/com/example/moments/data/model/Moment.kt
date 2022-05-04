package com.example.moments.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

data class Moment(
    @DocumentId
    val id: String = "",
    val caption: String = "",
    val creator: DocumentReference? = null,
    val media: String = "",
    val likeCount: Int = 0,
    val createdAt: Timestamp = Timestamp.now()
)