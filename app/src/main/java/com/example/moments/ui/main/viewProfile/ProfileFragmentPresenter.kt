package com.example.moments.ui.main.viewProfile

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileFragmentPresenter<V : IProfileView, I : IProfileInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), IProfilePresenter<V, I> {
    override fun onViewPrepared() {
        interactor?.let { it ->
            compositeDisposable.addAll(
                it.doQueryCurrentUserPost().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.getCurrentUserPosts(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    }),
                it.doGetCurrentUserModel().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
                        getView()?.getCurrentUserModel(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    }),
                it.doQueryCurrentUserMoment().compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({
//                        getView()?.getCurrentUserMoments(it)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }
}