package com.example.moments.ui.forgetPassword.stepThree

import dagger.Module
import dagger.Provides

@Module
class ForgetPasswordActivityStepThreeModule {
    @Provides
    internal fun provideForgetPasswordStepTwoInteractor(forgetPasswordActivityStepThreeInteractor: ForgetPasswordActivityStepThreeInteractor): IForgetPasswordActivityStepThreeInteractor =
        forgetPasswordActivityStepThreeInteractor

    @Provides
    internal fun provideForgetPasswordStepTwoPresenter(forgetPasswordActivityStepThreePresenter: ForgetPasswordActivityStepThreePresenter<IForgetPasswordActivityStepThreeView, IForgetPasswordActivityStepThreeInteractor>): IForgetPasswordActivityStepThreePresenter<IForgetPasswordActivityStepThreeView, IForgetPasswordActivityStepThreeInteractor> =
        forgetPasswordActivityStepThreePresenter
}