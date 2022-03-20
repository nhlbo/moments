package com.example.moments.ui.forgetPassword.stepThree

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IForgetPasswordActivityStepThreeView : IBaseView {
    fun openForgetPasswordActivityStepThree()
    fun showValidationMessage(errorCode: Int)
}

interface IForgetPasswordActivityStepThreeInteractor : IBaseInteractor {
}

interface IForgetPasswordActivityStepThreePresenter<V : IForgetPasswordActivityStepThreeView, I : IForgetPasswordActivityStepThreeInteractor> :
    IBasePresenter<V, I> {
    fun onForgetPasswordStepThreeContinueClicked(newPassword: String, confirmNewPassword: String)
}