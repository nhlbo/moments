package com.example.moments.ui.base

interface IBasePresenter <V : IBaseView, I : IBaseInteractor> {
    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?
}