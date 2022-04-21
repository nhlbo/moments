package com.example.moments.ui.main.settings

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface ISettingsActivityView : IBaseView {
}

interface ISettingsActivityPresenter<V : ISettingsActivityView, I : ISettingsActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformLogOut()
}

interface ISettingsActivityInteractor : IBaseInteractor {
    fun doPerformLogOut()
}
