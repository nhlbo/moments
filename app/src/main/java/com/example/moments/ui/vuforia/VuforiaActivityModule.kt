package com.example.moments.ui.vuforia

import dagger.Module
import dagger.Provides

@Module
class VuforiaActivityModule {

    @Provides
    internal fun provideVuforiaInteractor(vuforiaActivityInteractor: VuforiaActivityInteractor): IVuforiaActivityInteractor =
        vuforiaActivityInteractor

    @Provides
    internal fun provideVuforiaPresenter(vuforiaActivityPresenter: VuforiaActivityPresenter<IVuforiaActivityView, IVuforiaActivityInteractor>): IVuforiaActivityPresenter<IVuforiaActivityView, IVuforiaActivityInteractor> =
        vuforiaActivityPresenter
}