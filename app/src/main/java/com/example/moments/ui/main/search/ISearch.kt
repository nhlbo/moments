package com.example.moments.ui.main.search

import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Observable

interface ISearchView : IBaseView {
    fun onSearchResultCallback(listUser: List<User>)
}

interface ISearchInteractor : IBaseInteractor {
    fun doSearchUserByUsername(query: String) : Observable<List<User>>
}

interface ISearchPresenter<V : ISearchView, I : ISearchInteractor> : IBasePresenter<V, I> {
    fun onSearchDispatch(query: String)
}