package com.example.moments.di.module

import android.app.Application
import android.content.Context
import com.example.moments.data.preference.IPreferenceHelper
import com.example.moments.data.preference.PreferenceHelper
import com.example.moments.di.PreferenceInfo
import com.example.moments.util.AppConstants
import com.example.moments.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun providePrefHelper(preferenceHelper: PreferenceHelper): IPreferenceHelper = preferenceHelper

    @Provides
    @PreferenceInfo
    internal fun provideprefFileName(): String = AppConstants.PREF_NAME

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
}