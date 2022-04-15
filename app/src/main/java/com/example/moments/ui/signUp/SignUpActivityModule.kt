package com.example.moments.ui.signUp

import dagger.Module
import dagger.Provides

@Module
class   SignUpActivityModule {

    @Provides
    internal fun provideSignUpInteractor(signUpActivityInteractor: SignUpActivityInteractor): ISignUpActivityInteractor =
        signUpActivityInteractor

    @Provides
    internal fun provideSignUpPresenter(signUpActivityPresenter: SignUpActivityPresenter<ISignUpActivityView, ISignUpActivityInteractor>): ISignUpActivityPresenter<ISignUpActivityView, ISignUpActivityInteractor> =
        signUpActivityPresenter
}