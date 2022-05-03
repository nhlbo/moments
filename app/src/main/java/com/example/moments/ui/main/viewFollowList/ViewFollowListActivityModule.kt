package com.example.moments.ui.main.viewFollowList

import dagger.Module
import dagger.Provides

@Module
class ViewFollowListActivityModule {

    @Provides
    internal fun provideViewFollowListInteractor(viewFollowListActivityInteractor: ViewFollowListActivityInteractor): IViewFollowListActivityInteractor =
        viewFollowListActivityInteractor

    @Provides
    internal fun provideViewFollowListPresenter(viewFollowListActivityPresenter: ViewFollowListActivityPresenter<IViewFollowListActivityView, IViewFollowListActivityInteractor>): IViewFollowListActivityPresenter<IViewFollowListActivityView, IViewFollowListActivityInteractor> =
        viewFollowListActivityPresenter
}