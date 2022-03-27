package com.example.moments.ui.forgetPassword.stepTwo

import dagger.Module
import dagger.Provides

@Module
class ForgetPasswordActivityStepTwoModule {
    @Provides
    internal fun provideForgetPasswordStepTwoInteractor(forgetPasswordActivityStepTwoInteractor: ForgetPasswordActivityStepTwoInteractor): IForgetPasswordActivityStepTwoInteractor =
        forgetPasswordActivityStepTwoInteractor

    @Provides
    internal fun provideForgetPasswordStepTwoPresenter(forgetPasswordActivityStepTwoPresenter: ForgetPasswordActivityStepTwoPresenter<IForgetPasswordActivityStepTwoView, IForgetPasswordActivityStepTwoInteractor>): IForgetPasswordActivityStepTwoPresenter<IForgetPasswordActivityStepTwoView, IForgetPasswordActivityStepTwoInteractor> =
        forgetPasswordActivityStepTwoPresenter
}