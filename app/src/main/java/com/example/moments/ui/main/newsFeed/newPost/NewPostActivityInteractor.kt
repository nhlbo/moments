package com.example.moments.ui.main.newsFeed.newPost

import android.net.Uri
import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single
import javax.inject.Inject

class NewPostActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), INewPostActivityInteractor {
    override fun doUploadMedia(listMedia: List<ByteArray>): Single<List<Uri>> =
        firebaseHelper.performUploadListMedia(listMedia).toList()
}