package com.example.moments.ui.main.notification

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface INotificationView : IBaseView {
}

interface INotificationInteractor : IBaseInteractor {

}

interface INotificationPresenter<V : INotificationView, I : INotificationInteractor> :
    IBasePresenter<V, I> {

}