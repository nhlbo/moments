package com.example.moments.di.builder

import com.example.moments.ui.forgetPassword.stepOne.ForgetPasswordActivityStepOneModule
import com.example.moments.ui.forgetPassword.stepOne.ForgetPasswordActivityStepOneView
import com.example.moments.ui.forgetPassword.stepThree.ForgetPasswordActivityStepThreeModule
import com.example.moments.ui.forgetPassword.stepThree.ForgetPasswordActivityStepThreeView
import com.example.moments.ui.forgetPassword.stepTwo.ForgetPasswordActivityStepTwoModule
import com.example.moments.ui.forgetPassword.stepTwo.ForgetPasswordActivityStepTwoView
import com.example.moments.ui.login.LoginActivityModule
import com.example.moments.ui.login.LoginActivityView
import com.example.moments.ui.sign_up.SignUpActivityModule
import com.example.moments.ui.sign_up.SignUpActivityView
import com.example.moments.ui.start.StartActivityModule
import com.example.moments.ui.start.StartActivityView
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(StartActivityModule::class)])
    abstract fun bindStartActivity(): StartActivityView

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivityView

    @ContributesAndroidInjector(modules = [(SignUpActivityModule::class)])
    abstract fun bindSignUpActivity(): SignUpActivityView

    @ContributesAndroidInjector(modules = [(ForgetPasswordActivityStepOneModule::class)])
    abstract fun bindForgetPasswordActivityStepOne(): ForgetPasswordActivityStepOneView

    @ContributesAndroidInjector(modules = [(ForgetPasswordActivityStepTwoModule::class)])
    abstract fun bindForgetPasswordActivityStepTwo(): ForgetPasswordActivityStepTwoView

    @ContributesAndroidInjector(modules = [(ForgetPasswordActivityStepThreeModule::class)])
    abstract fun bindForgetPasswordActivityStepThree(): ForgetPasswordActivityStepThreeView
}