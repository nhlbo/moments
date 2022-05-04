package com.example.moments.ui.main.search

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ISearchInteractor {
    override fun doSearchUserByUsername(query: String): Observable<List<User>> =
        firebaseHelper.performQueryUserByUsername(query).debounce(1, TimeUnit.SECONDS)

    override fun queryAllPost(): Single<List<Post>> =
        firebaseHelper.performQueryFeedPost()
}