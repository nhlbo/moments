package com.example.moments.ui.main.EditProfile

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IEditProfileActivityView : IBaseView {
}

interface IEditProfileActivityPresenter<V : IEditProfileActivityView, I : IEditProfileActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformLogOut()
}

interface IEditProfileActivityInteractor : IBaseInteractor {
    fun doPerformLogOut()
}
