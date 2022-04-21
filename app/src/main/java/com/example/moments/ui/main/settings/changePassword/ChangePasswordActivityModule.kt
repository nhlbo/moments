package com.example.moments.ui.main.settings.changePassword

import dagger.Module
import dagger.Provides

@Module
class ChangePasswordActivityModule {

    @Provides
    internal fun provideChangePasswordActivityInteractor(interactor: ChangePasswordActivityInteractor): IChangePasswordInteractor =
        interactor

    @Provides
    internal fun provideChangePasswordActivityPresenter(presenter: ChangePasswordActivityPresenter<IChangePasswordView, IChangePasswordInteractor>): IChangePasswordPresenter<IChangePasswordView, IChangePasswordInteractor> =
        presenter
}