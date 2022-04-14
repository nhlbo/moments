package com.example.moments.ui.main.search

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class SearchProvider {
    @ContributesAndroidInjector(modules = [SearchModule::class])
    internal abstract fun provideSearchFragmentFactory(): SearchFragmentView
}