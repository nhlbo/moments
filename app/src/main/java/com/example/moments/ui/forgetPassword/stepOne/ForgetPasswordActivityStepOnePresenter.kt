package com.example.moments.ui.forgetPassword.stepOne

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForgetPasswordActivityStepOnePresenter<V : IForgetPasswordActivityStepOneView, I : IForgetPasswordActivityStepOneInteractor> @Inject internal constructor(
    interactor: I
) : BasePresenter<V, I>(
    interactor = interactor
),
    IForgetPasswordActivityStepOnePresenter<V, I> {
    override fun onForgetPasswordStepOneContinueClicked(email: String) {
        getView()?.openForgetPasswordActivityStepTwo(email)
    }


}