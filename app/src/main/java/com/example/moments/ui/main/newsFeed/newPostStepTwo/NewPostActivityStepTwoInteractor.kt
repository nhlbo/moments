package com.example.moments.ui.main.newsFeed.newPostStepTwo

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class NewPostActivityStepTwoInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), INewPostStepTwoInteractor {
    override fun doCreatePost(caption: String, listMedia: List<ByteArray>): Single<RetrievedPost> =
        firebaseHelper.performUploadListMedia(listMedia).toList().flatMap { listUri ->
            firebaseHelper.performAddPost(caption, listUri.map { it.toString() }).flatMap { post ->
                firebaseHelper.performQueryUserByReference(post.creator!!).map { user ->
                    RetrievedPost(post, user)
                }
            }
        }


}