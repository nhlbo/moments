package com.example.moments.ui.main.viewOtherProfile

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class OtherProfileActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IOtherProfileActivityInteractor {
    override fun doQueryCurrentUserPost(): Single<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun doGetCurrentUserModel(): Single<User> {
        TODO("Not yet implemented")
    }
}