package com.example.moments.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

open class Comment(
    @DocumentId
    val id: String = "",
    val creator: DocumentReference? = null,
    val content: String = "",
    val likeCount: Int = 0,
    val createdAt: Timestamp = Timestamp.now()
)


