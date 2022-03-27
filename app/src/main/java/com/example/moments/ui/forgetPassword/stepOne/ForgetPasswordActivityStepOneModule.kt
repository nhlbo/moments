package com.example.moments.ui.forgetPassword.stepOne

import dagger.Module
import dagger.Provides

@Module
class ForgetPasswordActivityStepOneModule {
    @Provides
    internal fun provideForgetPasswordStepOneInteractor(forgetPasswordActivityStepOneInteractor: ForgetPasswordActivityStepOneInteractor): IForgetPasswordActivityStepOneInteractor =
        forgetPasswordActivityStepOneInteractor

    @Provides
    internal fun provideForgetPasswordStepOnePresenter(forgetPasswordActivityStepOnePresenter: ForgetPasswordActivityStepOnePresenter<IForgetPasswordActivityStepOneView, IForgetPasswordActivityStepOneInteractor>): IForgetPasswordActivityStepOnePresenter<IForgetPasswordActivityStepOneView, IForgetPasswordActivityStepOneInteractor> =
        forgetPasswordActivityStepOnePresenter
}