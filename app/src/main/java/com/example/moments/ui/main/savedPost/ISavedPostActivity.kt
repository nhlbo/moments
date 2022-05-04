package com.example.moments.ui.main.savedPost

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface ISavedPostActivityView : IBaseView {
}

interface ISavedPostActivityPresenter<V : ISavedPostActivityView, I : ISavedPostActivityInteractor> :
    IBasePresenter<V, I> {

}

interface ISavedPostActivityInteractor : IBaseInteractor {
    fun doPerformLogOut()
}
