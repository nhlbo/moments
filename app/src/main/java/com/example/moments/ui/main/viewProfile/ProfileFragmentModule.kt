package com.example.moments.ui.main.viewProfile

import dagger.Module
import dagger.Provides

@Module
class ProfileFragmentModule {
    @Provides
    internal fun provideProfileInteractor(interactor: ProfileInteractor): IProfileInteractor =
        interactor

    @Provides
    internal fun provideProfilePresenter(presenter: ProfilePresenter<IProfileView, IProfileInteractor>): IProfilePresenter<IProfileView, IProfileInteractor> =
        presenter
}