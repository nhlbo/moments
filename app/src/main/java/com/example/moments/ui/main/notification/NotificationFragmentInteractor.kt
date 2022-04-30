package com.example.moments.ui.main.notification

import com.example.moments.data.firebase.FirebaseHelper
import com.example.moments.data.model.RetrievedNotification
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.ui.base.BaseInteractor
import io.reactivex.Single

import javax.inject.Inject

class NotificationFragmentInteractor @Inject constructor(
    preferenceHelper: PreferenceHelper,
    firebaseHelper: FirebaseHelper
) : BaseInteractor(preferenceHelper, firebaseHelper), INotificationInteractor {
    override fun doQueryNotification(): Single<List<RetrievedNotification>> =
        firebaseHelper.performQueryNotification().flattenAsObservable { it }
            .flatMapSingle { notification ->
                when (notification.creator) {
                    null -> Single.just(RetrievedNotification(notification))
                    else -> firebaseHelper.performQueryUserByReference(notification.creator)
                        .map { user ->
                            RetrievedNotification(notification, user)
                        }
                }
            }
            .toList()
}