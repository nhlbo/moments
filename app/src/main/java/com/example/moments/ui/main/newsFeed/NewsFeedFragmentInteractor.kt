package com.example.moments.ui.main.newsFeed

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Single
import javax.inject.Inject

class NewsFeedFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), INewsFeedInteractor {
    override fun doQueryFeedPost(): Single<List<DocumentSnapshot>> = firebaseHelper.performQueryFeedPost()
}