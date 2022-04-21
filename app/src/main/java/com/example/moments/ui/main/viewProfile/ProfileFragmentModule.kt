package com.example.moments.ui.main.viewProfile

import dagger.Module
import dagger.Provides

@Module
class ProfileFragmentModule {
    @Provides
    internal fun provideProfileInteractor(interactor: ProfileFragmentInteractor): IProfileInteractor =
        interactor

    @Provides
    internal fun provideProfilePresenter(presenter: ProfileFragmentPresenter<IProfileView, IProfileInteractor>): IProfilePresenter<IProfileView, IProfileInteractor> =
        presenter
}