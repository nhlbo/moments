package com.example.moments.ui.start

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IStartActivityView : IBaseView {
    fun openMainActivity()
    fun openLoginActivity()
}

interface IStartActivityInteractor : IBaseInteractor {
}

interface IStartActivityPresenter<V : IStartActivityView, I : IStartActivityInteractor> :
    IBasePresenter<V, I> {
}

