package com.example.moments.ui.main.new_message

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface INewMessageActivityView : IBaseView {
}

interface INewMessageActivityPresenter<V : INewMessageActivityView, I : INewMessageActivityInteractor> :
    IBasePresenter<V, I> {
}

interface INewMessageActivityInteractor : IBaseInteractor {
}