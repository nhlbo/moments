package com.example.moments.ui.forgetPassword.stepOne

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IForgetPasswordActivityStepOneView : IBaseView {
    fun openForgetPasswordActivityStepTwo(email: String)
    fun showValidationMessage(errorCode: Int)
}

interface IForgetPasswordActivityStepOneInteractor : IBaseInteractor {
    fun doRequestForgetPassword(email:String)
}

interface IForgetPasswordActivityStepOnePresenter<V : IForgetPasswordActivityStepOneView, I : IForgetPasswordActivityStepOneInteractor> :
    IBasePresenter<V, I> {
    fun onForgetPasswordStepOneContinueClicked(email: String)
}