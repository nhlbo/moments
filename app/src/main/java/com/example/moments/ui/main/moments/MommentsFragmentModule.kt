package com.example.moments.ui.main.moments

import dagger.Module
import dagger.Provides

@Module
class MommentsFragmentModule {
    @Provides
    internal fun provideMommentInteractor(interactor: MommentsFragmentInteractor): IMommentsInteractor =
        interactor

    @Provides
    internal fun provideMommentPresenter(presenter: MommentsFragmentPresenter<IMommentsView, IMommentsInteractor>): IMommentsPresenter<IMommentsView, IMommentsInteractor> =
        presenter
}