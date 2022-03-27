package com.example.moments.ui.login

import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {

    @Provides
    internal fun provideLoginInteractor(loginActivityInteractor: LoginActivityInteractor): ILoginActivityInteractor =
        loginActivityInteractor

    @Provides
    internal fun provideLoginPresenter(loginActivityPresenter: LoginActivityPresenter<ILoginActivityView, ILoginActivityInteractor>): ILoginActivityPresenter<ILoginActivityView, ILoginActivityInteractor> =
        loginActivityPresenter
}