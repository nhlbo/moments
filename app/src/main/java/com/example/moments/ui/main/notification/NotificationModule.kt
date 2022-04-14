package com.example.moments.ui.main.notification

import dagger.Module
import dagger.Provides

@Module
class NotificationModule {
    @Provides
    internal fun provideNotificationInteractor(interactor: NotificationInteractor): INotificationInteractor =
        interactor

    @Provides
    fun provideNotificationPresenter(presenter: NotificationPresenter<INotificationView, INotificationInteractor>): INotificationPresenter<INotificationView, INotificationInteractor> =
        presenter
}