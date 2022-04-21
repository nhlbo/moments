package com.example.moments.ui.main.newsFeed

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class NewsFeedFragmentProvider {
    @ContributesAndroidInjector(modules = [NewsFeedFragmentModule::class])
    internal abstract fun provideNewsFeedFragmentFactory(): NewsFeedFragmentView
}