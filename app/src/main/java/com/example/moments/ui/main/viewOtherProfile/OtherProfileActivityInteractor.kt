package com.example.moments.ui.main.viewOtherProfile

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class OtherProfileActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IOtherProfileActivityInteractor {
    override fun doQueryUserPostByUserId(userId: String): Single<List<RetrievedPost>> =
        firebaseHelper.performQueryUserPostByUserId(userId).flattenAsObservable { it }
            .flatMapSingle { post ->
                firebaseHelper.performQueryUserByReference(post.creator!!).map { user ->
                    RetrievedPost(post, user)
                }
            }
            .toList()

    override fun doQueryUserById(userId: String): Single<User> =
        firebaseHelper.performQueryUserById(userId)
}