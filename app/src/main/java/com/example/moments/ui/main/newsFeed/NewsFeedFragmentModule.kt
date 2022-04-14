package com.example.moments.ui.main.newsFeed

import dagger.Module
import dagger.Provides

@Module
class NewsFeedFragmentModule {
    @Provides
    internal fun provideNewsFeedInteractor(interactor: NewsFeedInteractor): INewsFeedInteractor =
        interactor

    @Provides
    internal fun provideNewsFeedPresenter(presenter: NewsFeedPresenter<INewsFeedView, INewsFeedInteractor>): INewsFeedPresenter<INewsFeedView, INewsFeedInteractor> =
        presenter
}