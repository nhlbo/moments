package com.example.moments.ui.base

import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : IBaseView, I : IBaseInteractor> internal constructor(
    protected var interactor: I?
) : IBasePresenter<V, I> {
    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    override fun onAttach(view: V?) {
        this.view = view
    }

    override fun getView(): V? = view

    override fun onDetach() {
        view = null
        interactor = null
    }
}