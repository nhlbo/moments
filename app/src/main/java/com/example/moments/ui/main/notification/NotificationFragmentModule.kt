package com.example.moments.ui.main.notification

import dagger.Module
import dagger.Provides

@Module
class NotificationFragmentModule {
    @Provides
    internal fun provideNotificationInteractor(interactor: NotificationFragmentInteractor): INotificationInteractor =
        interactor

    @Provides
    fun provideNotificationPresenter(presenter: NotificationFragmentPresenter<INotificationView, INotificationInteractor>): INotificationPresenter<INotificationView, INotificationInteractor> =
        presenter
}