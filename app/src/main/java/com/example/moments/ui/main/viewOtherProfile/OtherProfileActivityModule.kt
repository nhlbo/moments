package com.example.moments.ui.main.viewOtherProfile

import com.example.moments.ui.main.editProfile.EditProfileActivityInteractor
import com.example.moments.ui.main.editProfile.EditProfileActivityPresenter
import com.example.moments.ui.main.editProfile.IEditProfileActivityInteractor
import com.example.moments.ui.main.editProfile.IEditProfileActivityPresenter
import com.example.moments.ui.main.editProfile.IEditProfileActivityView
import dagger.Module
import dagger.Provides

@Module
class OtherProfileActivityModule {
    @Provides
    internal fun provideOtherProfileInteractor(otherProfileActivityInteractor: OtherProfileActivityInteractor): IOtherProfileActivityInteractor =
        otherProfileActivityInteractor

    @Provides
    internal fun provideEditProfilePresenter(otherProfileActivityPresenter: OtherProfileActivityPresenter<IOtherProfileActivityView, IOtherProfileActivityInteractor>): OtherProfileActivityPresenter<IOtherProfileActivityView, IOtherProfileActivityInteractor> =
        otherProfileActivityPresenter
}