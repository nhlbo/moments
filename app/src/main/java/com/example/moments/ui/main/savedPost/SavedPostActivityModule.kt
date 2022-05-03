package com.example.moments.ui.main.savedPost

import dagger.Module
import dagger.Provides

@Module
class SavedPostActivityModule {

    @Provides
    internal fun provideSavedPostInteractor(savedPostActivityInteractor: SavedPostActivityInteractor): ISavedPostActivityInteractor =
        savedPostActivityInteractor

    @Provides
    internal fun provideSavedPostPresenter(savedPostActivityPresenter: SavedPostActivityPresenter<ISavedPostActivityView, ISavedPostActivityInteractor>): ISavedPostActivityPresenter<ISavedPostActivityView, ISavedPostActivityInteractor> =
        savedPostActivityPresenter
}