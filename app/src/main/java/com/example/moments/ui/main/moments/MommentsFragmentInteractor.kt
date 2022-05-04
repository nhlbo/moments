package com.example.moments.ui.main.moments

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedMoment
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MommentsFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), IMommentsInteractor {
    override fun doLikeMoment(momentId: String): Completable =
        firebaseHelper.performLikeMoment(momentId)


    override fun doUnlikeMoment(momentId: String): Completable =
        firebaseHelper.performUnlikeMoment(momentId)

    override fun doQueryFeedMoment(): Single<List<RetrievedMoment>> =
        firebaseHelper.performQueryFeedMoment().flattenAsObservable { it }
            .flatMapSingle { moment ->
                firebaseHelper.performQueryUserByReference(moment.creator!!).map { user ->
                    RetrievedMoment(moment, user)
                }
            }
            .toList()
}