package com.example.moments.ui.main.chat

import dagger.Module
import dagger.Provides

@Module
class ChatActivityModule {

    @Provides
    internal fun provideChatInteractor(chatActivityInteractor: ChatActivityInteractor): IChatActivityInteractor =
        chatActivityInteractor

    @Provides
    internal fun provideChatPresenter(chatActivityPresenter: ChatActivityPresenter<IChatActivityView, IChatActivityInteractor>): IChatActivityPresenter<IChatActivityView, IChatActivityInteractor> =
        chatActivityPresenter
}