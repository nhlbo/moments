package com.example.moments.ui.main

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IMainActivityView : IBaseView

interface IMainActivityInteractor : IBaseInteractor

interface IMainActivityPresenter<V : IMainActivityView, I : IMainActivityInteractor> :
    IBasePresenter<V, I>