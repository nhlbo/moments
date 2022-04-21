package com.example.moments.ui.main.moments

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MommentsFragmentProvider {
    @ContributesAndroidInjector(modules = [MommentsFragmentModule::class])
    internal abstract fun provideMommentsFragmentFactory(): MomentsFragmentView
}