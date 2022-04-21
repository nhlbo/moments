package com.example.moments.ui.main.newsFeed

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class NewsFeedFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), INewsFeedInteractor {
    override fun doQueryFeedPost(): Single<List<RetrievedPost>> =
        firebaseHelper.performQueryFeedPost().flattenAsObservable { it }
            .flatMapSingle { post ->
                firebaseHelper.performQueryUserByReference(post.creator!!).map { user ->
                    RetrievedPost(post, user)
                }
            }
            .toList()
}