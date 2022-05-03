package com.example.moments.ui.main.editProfile

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import javax.inject.Inject

class EditProfileActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IEditProfileActivityInteractor {
    override fun doPerformEditProfile(username: String, bio: String): Completable = firebaseHelper.performEditProfile(username, bio)
    override fun doUploadCurrentUserAvatar(media:ByteArray): Completable =
        firebaseHelper.performUploadMedia(media).flatMapCompletable { uri ->
            firebaseHelper.performUpdateCurrentUserAvatar(uri.toString())
        }
}