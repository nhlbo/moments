package com.example.moments.ui.main.editProfile

import com.example.moments.ui.base.BasePresenter
import com.example.moments.ui.main.EditProfile.IEditProfileActivityInteractor
import com.example.moments.ui.main.EditProfile.IEditProfileActivityPresenter
import com.example.moments.ui.main.EditProfile.IEditProfileActivityView
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class EditProfileActivityPresenter<V : IEditProfileActivityView, I : IEditProfileActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IEditProfileActivityPresenter<V, I> {

    override fun onPerformLogOut() {
        interactor?.doPerformLogOut()
    }
}