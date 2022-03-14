package com.example.moments.ui.start

import dagger.Module
import dagger.Provides

@Module
class StartActivityModule {

    @Provides
    internal fun provideStartInteractor(startActivityInteractor: StartActivityInteractor): IStartActivityInteractor =
        startActivityInteractor

    @Provides
    internal fun provideStartPresenter(startActivityPresenter: StartActivityPresenter<IStartActivityView, IStartActivityInteractor>): IStartActivityPresenter<IStartActivityView, IStartActivityInteractor> =
        startActivityPresenter
}