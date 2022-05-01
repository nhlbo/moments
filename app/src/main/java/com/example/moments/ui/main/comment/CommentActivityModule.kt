package com.example.moments.ui.main.comment

import dagger.Module
import dagger.Provides

@Module
class CommentActivityModule {
    @Provides
    internal fun provideCommentActivityInteractor(interactor: CommentActivityInteractor): ICommentActivityInteractor =
        interactor

    @Provides
    internal fun provideCommentActivityPresenter(presenter: CommentActivityPresenter<ICommentActivityView, ICommentActivityInteractor>): ICommentActivityPresenter<ICommentActivityView, ICommentActivityInteractor> =
        presenter
}