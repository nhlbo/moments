package com.example.moments.ui.main.search

import com.example.moments.ui.base.BasePresenter
import com.example.moments.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchFragmentPresenter<V : ISearchView, I : ISearchInteractor> @Inject internal constructor(
    interactor: I,
    schedulerProvider: SchedulerProvider,
    disposable: CompositeDisposable
) : BasePresenter<V, I>(
    interactor = interactor,
    schedulerProvider = schedulerProvider,
    compositeDisposable = disposable
), ISearchPresenter<V, I> {
}