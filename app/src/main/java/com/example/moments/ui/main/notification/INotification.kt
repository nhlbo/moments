package com.example.moments.ui.main.notification

import com.example.moments.data.model.RetrievedNotification
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface INotificationView : IBaseView {
}

interface INotificationInteractor : IBaseInteractor {
    fun doQueryNotification(): Single<List<RetrievedNotification>>
}

interface INotificationPresenter<V : INotificationView, I : INotificationInteractor> :
    IBasePresenter<V, I> {

}