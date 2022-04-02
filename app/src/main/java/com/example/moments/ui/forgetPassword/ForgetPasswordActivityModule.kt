package com.example.moments.ui.forgetPassword

import dagger.Module
import dagger.Provides

@Module
class ForgetPasswordActivityModule {
    @Provides
    internal fun provideForgetPasswordInteractor(forgetPasswordActivityInteractor: ForgetPasswordActivityInteractor): IForgetPasswordActivityInteractor =
        forgetPasswordActivityInteractor

    @Provides
    internal fun provideForgetPasswordPresenter(forgetPasswordActivityPresenter: ForgetPasswordActivityPresenter<IForgetPasswordActivityView, IForgetPasswordActivityInteractor>): IForgetPasswordActivityPresenter<IForgetPasswordActivityView, IForgetPasswordActivityInteractor> =
        forgetPasswordActivityPresenter
}