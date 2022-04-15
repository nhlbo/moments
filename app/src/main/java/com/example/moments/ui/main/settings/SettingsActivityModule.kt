package com.example.moments.ui.main.settings

import dagger.Module
import dagger.Provides

@Module
class SettingsActivityModule {

    @Provides
    internal fun provideSettingsInteractor(settingsActivityInteractor: SettingsActivityInteractor): ISettingsActivityInteractor =
        settingsActivityInteractor

    @Provides
    internal fun provideSettingsPresenter(settingsActivityPresenter: SettingsActivityPresenter<ISettingsActivityView, ISettingsActivityInteractor>): ISettingsActivityPresenter<ISettingsActivityView, ISettingsActivityInteractor> =
        settingsActivityPresenter
}