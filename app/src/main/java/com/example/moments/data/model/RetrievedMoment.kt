package com.example.moments.data.model

import com.google.firebase.Timestamp

class RetrievedMoment(moment: Moment, user: User) {
    var id: String = moment.id
    var creator: User = user
    var caption: String = moment.caption
    var media: String = moment.media
    var createdAt: Timestamp = moment.createdAt
    var likeCount: Int = moment.likeCount
}