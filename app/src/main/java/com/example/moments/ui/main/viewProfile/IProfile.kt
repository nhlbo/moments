package com.example.moments.ui.main.viewProfile

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface IProfileView : IBaseView

interface IProfileInteractor : IBaseInteractor

interface IProfilePresenter<V : IProfileView, I : IProfileInteractor> : IBasePresenter<V, I>