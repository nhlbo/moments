package com.example.moments.ui.main.newsFeed

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class NewsFeedFragmentModule {
    @Provides
    internal fun provideNewsFeedInteractor(interactor: NewsFeedFragmentInteractor): INewsFeedInteractor =
        interactor

    @Provides
    internal fun provideNewsFeedPresenter(presenter: NewsFeedFragmentPresenter<INewsFeedView, INewsFeedInteractor>): INewsFeedPresenter<INewsFeedView, INewsFeedInteractor> =
        presenter

    @Provides
    internal fun provideLinearLayoutManager(fragment: NewsFeedFragmentView): LinearLayoutManager =
        LinearLayoutManager(fragment.activity)
}