package com.example.moments.ui.main.moments

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IMommentsInteractor : IBaseInteractor {
}

interface IMommentsView : IBaseView

interface IMommentsPresenter<V : IMommentsView, I : IMommentsInteractor> : IBasePresenter<V, I>