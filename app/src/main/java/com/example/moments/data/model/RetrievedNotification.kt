package com.example.moments.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class RetrievedNotification(notification: Notification, user: User? = null) {
    var id: String = notification.id
    var type: String = notification.type
    var creator: User? = user
    var post: DocumentReference? = notification.post
    var media: String = notification.media
    var btnClicked: Boolean = notification.btnClicked
    var createdAt: Timestamp = notification.createdAt
}