package com.example.moments.ui.main.search

import dagger.Module
import dagger.Provides

@Module
class SearchFragmentModule {
    @Provides
    internal fun provideSearchInteractor(interactor: SearchFragmentInteractor):ISearchInteractor = interactor

    @Provides
    internal fun provideSearchPresenter(presenter: SearchFragmentPresenter<ISearchView, ISearchInteractor>): ISearchPresenter<ISearchView, ISearchInteractor>
    = presenter
}