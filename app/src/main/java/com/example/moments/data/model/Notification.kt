package com.example.moments.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

data class Notification(
    @DocumentId
    val id: String = "",
    val type: String = "",
    val caption : String = "",
    val creator: DocumentReference? = null,
    val post: DocumentReference? = null,
    val media: String = "",
    val btnClicked: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
)
