package com.example.moments.ui.main.viewPost

import com.example.moments.ui.main.viewOtherProfile.*
import dagger.Module
import dagger.Provides
@Module
class ViewPostActivityModule {
    @Provides
    internal fun provideViewPostInteractor(viewPostActivityInteractor: ViewPostActivityInteractor): IViewPostInteractor =
        viewPostActivityInteractor

    @Provides
    internal fun provideViewPostPresenter(viewPostActivityPresenter: ViewPostPresenter<IViewPostView, IViewPostInteractor>): IViewPostPresenter<IViewPostView, IViewPostInteractor> =
        viewPostActivityPresenter
}