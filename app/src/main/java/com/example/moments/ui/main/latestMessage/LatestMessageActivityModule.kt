package com.example.moments.ui.main.latestMessage

import dagger.Module
import dagger.Provides

@Module
class LatestMessageActivityModule {

    @Provides
    internal fun provideLatestMessageInteractor(latestMessageActivityInteractor: LatestMessageActivityInteractor): ILatestMessageActivityInteractor =
        latestMessageActivityInteractor

    @Provides
    internal fun provideLatestMessagePresenter(latestMessageActivityPresenter: LatestMessageActivityPresenter<ILatestMessageActivityView, ILatestMessageActivityInteractor>): ILatestMessageActivityPresenter<ILatestMessageActivityView, ILatestMessageActivityInteractor> =
        latestMessageActivityPresenter
}