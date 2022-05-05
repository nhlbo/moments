package com.example.moments.ui.main.viewOtherProfile

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Moment
import com.example.moments.data.model.OtherUser
import com.example.moments.data.model.Post
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class OtherProfileActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IOtherProfileActivityInteractor {
    override fun doQueryUserPostByUserId(userId: String): Single<List<Post>> =
        firebaseHelper.performQueryUserPostByUserId(userId)

    override fun doQueryUserById(userId: String): Single<OtherUser> =
        firebaseHelper.performQueryUserById(userId).flatMap { user ->
            firebaseHelper.performQueryUserIsFollowed(user.id).map { following ->
                OtherUser(user, following)
            }
        }

    override fun doQueryUserMoment(userId: String): Single<List<Moment>> =
        firebaseHelper.performQueryUserMoment(userId)

    override fun doGetCurrentUserId(): String = firebaseHelper.getCurrentUserId()
}