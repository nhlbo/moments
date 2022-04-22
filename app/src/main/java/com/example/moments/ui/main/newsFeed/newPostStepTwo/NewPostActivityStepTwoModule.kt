package com.example.moments.ui.main.newsFeed.newPostStepTwo

import dagger.Module
import dagger.Provides

@Module
class NewPostActivityStepTwoModule {
    @Provides
    internal fun provideNewPostActivityStepTwoInteractor(interactor: NewPostActivityStepTwoInteractor): INewPostStepTwoInteractor =
        interactor

    @Provides
    internal fun provideNewPostActivityStepTwoPresenter(presenter: NewPostActivityStepTwoPresenter<INewPostStepTwoView, INewPostStepTwoInteractor>):
            INewPostStepTwoPresenter<INewPostStepTwoView, INewPostStepTwoInteractor> =
        presenter
}