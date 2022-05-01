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
    override fun onViewPrepared(userId: String) {
        interactor?.let {
            compositeDisposable.addAll(
                it.doQueryUserById(userId).compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({ user ->
                        getView()?.getCurrentUserModel(user)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    }),
                it.doQueryUserPostByUserId(userId)
                    .compose(schedulerProvider.ioToMainSingleScheduler())
                    .subscribe({ posts->
                        getView()?.getCurrentUserPosts(posts)
                    }, {
                        getView()?.showCustomToastMessage(it.localizedMessage)
                    })
            )
        }
    }

}