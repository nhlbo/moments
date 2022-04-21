package com.example.moments.di.builder

import com.example.moments.ui.forgetPassword.ForgetPasswordActivityModule
import com.example.moments.ui.forgetPassword.ForgetPasswordActivityView
import com.example.moments.ui.login.LoginActivityModule
import com.example.moments.ui.login.LoginActivityView
import com.example.moments.ui.main.MainActivityView
import com.example.moments.ui.main.chat.ChatActivityModule
import com.example.moments.ui.main.chat.ChatActivityView
import com.example.moments.ui.main.editProfile.EditProfileActivityModule
import com.example.moments.ui.main.editProfile.EditProfileActivityView
import com.example.moments.ui.main.latestMessage.LatestMessageActivityModule
import com.example.moments.ui.main.latestMessage.LatestMessageActivityView
import com.example.moments.ui.main.newMessage.NewMessageActivityModule
import com.example.moments.ui.main.newMessage.NewMessageActivityView
import com.example.moments.ui.main.newsFeed.NewsFeedFragmentProvider
import com.example.moments.ui.main.notification.NotificationFragmentProvider
import com.example.moments.ui.main.search.SearchFragmentProvider
import com.example.moments.ui.main.settings.SettingsActivityModule
import com.example.moments.ui.main.settings.SettingsActivityView
import com.example.moments.ui.main.viewProfile.ProfileFragmentProvider
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

    @ContributesAndroidInjector(modules = [(SettingsActivityModule::class)])
    abstract fun bindSettingsActivity(): SettingsActivityView

    @ContributesAndroidInjector(modules = [(EditProfileActivityModule::class)])
    abstract fun bindEditProfileActivity(): EditProfileActivityView

    @ContributesAndroidInjector(modules = [(NewsFeedFragmentProvider::class), (SearchFragmentProvider::class), (NotificationFragmentProvider::class), (ProfileFragmentProvider::class)])
    abstract fun bindMainActivity(): MainActivityView

    @ContributesAndroidInjector(modules = [(LatestMessageActivityModule::class)])
    abstract fun bindMessageActitity(): LatestMessageActivityView
}