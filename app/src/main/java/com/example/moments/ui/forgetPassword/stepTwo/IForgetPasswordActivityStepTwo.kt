package com.example.moments.ui.forgetPassword.stepTwo

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IForgetPasswordActivityStepTwoView : IBaseView {
    fun openForgetPasswordActivityStepThree(email: String)
    fun showValidationMessage(errorCode: Int)
}

interface IForgetPasswordActivityStepTwoInteractor : IBaseInteractor

interface IForgetPasswordActivityStepTwoPresenter<V : IForgetPasswordActivityStepTwoView, I : IForgetPasswordActivityStepTwoInteractor> :
    IBasePresenter<V, I> {
    fun onForgetPasswordStepTwoContinueClicked(email: String, code: String)
}