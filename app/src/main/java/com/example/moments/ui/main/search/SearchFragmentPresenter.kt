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
    override fun onSearchDispatch(query: String) {
        if (query.isNotEmpty()) {
            interactor?.let {
                compositeDisposable.add(
                    it.doSearchUserByUsername(query)
                        .compose(schedulerProvider.ioToMainObservableScheduler())
                        .subscribe({
                            getView()?.onSearchResultCallback(it)
                        }, {
                            getView()?.showCustomToastMessage(it.localizedMessage)
                        })
                )
            }
        }
    }
}