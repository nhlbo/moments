package com.example.moments.ui.main.notification

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class NotificationFragmentProvider {
    @ContributesAndroidInjector(modules = [(NotificationFragmentModule::class)])
    internal abstract fun provideNotificationFragmentFactory(): NotificationFragmentView
}