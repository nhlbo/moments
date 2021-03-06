package com.example.moments.ui.main.newMessage

import dagger.Module
import dagger.Provides

@Module
class NewMessageActivityModule {

    @Provides
    internal fun provideNewMessageInteractor(newMessageActivityInteractor: NewMessageActivityInteractor): INewMessageActivityInteractor =
        newMessageActivityInteractor

    @Provides
    internal fun provideNewMessagePresenter(newMessageActivityPresenter: NewMessageActivityPresenter<INewMessageActivityView, INewMessageActivityInteractor>): INewMessageActivityPresenter<INewMessageActivityView, INewMessageActivityInteractor> =
        newMessageActivityPresenter
}