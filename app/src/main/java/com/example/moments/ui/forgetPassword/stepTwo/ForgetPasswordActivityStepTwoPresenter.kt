package com.example.moments.ui.forgetPassword.stepTwo

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForgetPasswordActivityStepTwoPresenter<V : IForgetPasswordActivityStepTwoView, I : IForgetPasswordActivityStepTwoInteractor> @Inject internal constructor(
    interactor: I
) : BasePresenter<V, I>(
    interactor = interactor
),
    IForgetPasswordActivityStepTwoPresenter<V, I> {
    override fun onForgetPasswordStepTwoContinueClicked(email: String, code: String) {
        getView()?.openForgetPasswordActivityStepThree(email)
    }


}