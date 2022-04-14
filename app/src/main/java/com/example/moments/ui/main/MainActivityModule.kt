package com.example.moments.ui.main

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMainInteractor(mainActivityInteractor: MainActivityInteractor): IMainActivityInteractor =
        mainActivityInteractor

    @Provides
    internal fun provideMainPresenter(mainActivityPresenter: MainActivityPresenter<IMainActivityView, IMainActivityInteractor>): IMainActivityPresenter<IMainActivityView, IMainActivityInteractor> =
        mainActivityPresenter
}