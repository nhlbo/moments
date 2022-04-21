package com.example.moments.ui.main.newsFeed.newPost

import dagger.Module
import dagger.Provides

@Module
class NewPostActivityModule {
    @Provides
    internal fun provideNewPostInteractor(interactor: NewPostActivityInteractor): INewPostActivityInteractor =
        interactor

    @Provides
    internal fun provideNewPostPresenter(presenter: NewPostActivityPresenter<INewPostActivityView, INewPostActivityInteractor>): INewPostActivityPresenter<INewPostActivityView, INewPostActivityInteractor> =
        presenter
}