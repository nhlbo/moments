package com.example.moments.ui.main.viewProfile

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Moment
import com.example.moments.data.model.Post
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProfileFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IProfileInteractor {
    override fun doQueryCurrentUserPost(): Single<List<Post>> =
        firebaseHelper.performQueryCurrentUserPost()

    override fun doGetCurrentUserModel(): Single<User> = firebaseHelper.getCurrentUserModel()

    override fun doQueryCurrentUserMoment(): Single<List<Moment>> =
        firebaseHelper.performQueryCurrentUserMoment()
}