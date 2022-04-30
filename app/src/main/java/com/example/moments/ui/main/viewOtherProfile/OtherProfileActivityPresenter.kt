package com.example.moments.ui.main.viewOtherProfile

import com.example.moments.ui.base.BasePresenter
import com.example.moments.ui.main.editProfile.IEditProfileActivityInteractor
import com.example.moments.ui.main.editProfile.IEditProfileActivityPresenter
import com.example.moments.ui.main.editProfile.IEditProfileActivityView
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OtherProfileActivityPresenter<V : IOtherProfileActivityView, I : IOtherProfileActivityInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
),
    IOtherProfileActivityPresenter<V, I> {
    override fun onViewPrepared() {
        TODO("Not yet implemented")
    }

}