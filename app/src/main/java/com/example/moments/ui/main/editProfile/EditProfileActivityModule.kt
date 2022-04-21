package com.example.moments.ui.main.editProfile

import com.example.moments.ui.main.EditProfile.IEditProfileActivityInteractor
import com.example.moments.ui.main.EditProfile.IEditProfileActivityPresenter
import com.example.moments.ui.main.EditProfile.IEditProfileActivityView
import dagger.Module
import dagger.Provides

@Module
class EditProfileActivityModule {

    @Provides
    internal fun provideEditProfileInteractor(editProfileActivityInteractor: EditProfileActivityInteractor): IEditProfileActivityInteractor =
        editProfileActivityInteractor

    @Provides
    internal fun provideEditProfilePresenter(editProfileActivityPresenter: EditProfileActivityPresenter<IEditProfileActivityView, IEditProfileActivityInteractor>): IEditProfileActivityPresenter<IEditProfileActivityView, IEditProfileActivityInteractor> =
        editProfileActivityPresenter
}