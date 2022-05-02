package com.example.moments.ui.main.viewFollowList

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class ViewFollowListActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IViewFollowListActivityInteractor {
    override fun doPerformQueryFollowerCurrentUser(): Single<List<User>> =
        firebaseHelper.performQueryFollowerCurrentUser()

    override fun doPerformQueryFollowingCurrentUser(): Single<List<User>> =
        firebaseHelper.performQueryFollowingCurrentUser()
}