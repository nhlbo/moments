package com.example.moments.ui.main.search

import dagger.Module
import dagger.Provides

@Module
class SearchModule {
    @Provides
    internal fun provideSearchInteractor(interactor: SearchInteractor):ISearchInteractor = interactor

    @Provides
    internal fun provideSearchPresenter(presenter: SearchPresenter<ISearchView, ISearchInteractor>): ISearchPresenter<ISearchView, ISearchInteractor>
    = presenter
}