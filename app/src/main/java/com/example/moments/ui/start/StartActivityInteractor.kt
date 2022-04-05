package com.example.moments.ui.start

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.User
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class StartActivityInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IStartActivityInteractor {
    override fun test(): Single<DocumentSnapshot> {
        return firebaseHelper.performAddPost("123", arrayListOf())
    }
}