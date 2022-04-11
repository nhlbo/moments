package com.example.moments.di.builder

import com.example.moments.ui.forgetPassword.ForgetPasswordActivityModule
import com.example.moments.ui.forgetPassword.ForgetPasswordActivityView
import com.example.moments.ui.login.LoginActivityModule
import com.example.moments.ui.login.LoginActivityView
import com.example.moments.ui.main.chat.ChatActivityModule
import com.example.moments.ui.main.chat.ChatActivityView
import com.example.moments.ui.main.newMessage.NewMessageActivityModule
import com.example.moments.ui.main.newMessage.NewMessageActivityView
import com.example.moments.ui.signUp.SignUpActivityModule
import com.example.moments.ui.signUp.SignUpActivityView
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

    @ContributesAndroidInjector(modules = [(ForgetPasswordActivityModule::class)])
    abstract fun bindForgetPasswordActivityStepOne(): ForgetPasswordActivityView

    @ContributesAndroidInjector(modules = [(NewMessageActivityModule::class)])
    abstract fun bindNewMessageActivity(): NewMessageActivityView

    @ContributesAndroidInjector(modules = [(ChatActivityModule::class)])
    abstract fun bindChatActivity(): ChatActivityView
}