package com.example.moments.ui.main.search

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class SearchFragmentProvider {
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    internal abstract fun provideSearchFragmentFactory(): SearchFragmentView
}