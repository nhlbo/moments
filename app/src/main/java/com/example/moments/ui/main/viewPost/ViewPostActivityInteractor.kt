package com.example.moments.ui.main.viewPost

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ViewPostActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IViewPostInteractor {
    override fun doQueryFeedPost() : Single<RetrievedPost>{
        TODO("Not yet implemented")
    }

    override fun doLikePost(postId: String): Completable {
        TODO("Not yet implemented")
    }

    override fun doUnlikePost(postId: String): Completable {
        TODO("Not yet implemented")
    }
}