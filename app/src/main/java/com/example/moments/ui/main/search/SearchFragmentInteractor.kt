package com.example.moments.ui.main.search

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ISearchInteractor {
    override fun doSearchUserByUsername(query: String): Observable<List<User>> =
        firebaseHelper.performQueryUserByUsername(query).debounce(1, TimeUnit.SECONDS)
}