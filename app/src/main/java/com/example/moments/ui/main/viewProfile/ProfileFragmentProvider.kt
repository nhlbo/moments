package com.example.moments.ui.main.viewProfile

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ProfileFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideProfileFragmentFactory(): ProfileFragmentView
}