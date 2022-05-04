package com.example.moments.ui.main.savedPost

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.Post
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class SavedPostActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), ISavedPostActivityInteractor {
    override fun doPerformQueryBookmarkPost(): Single<List<Post>> =
        firebaseHelper.performQueryBookmarkPost()

}