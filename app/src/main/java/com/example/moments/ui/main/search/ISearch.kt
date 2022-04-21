package com.example.moments.ui.main.search

import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView

interface ISearchView : IBaseView {
}

interface ISearchInteractor : IBaseInteractor {

}

interface ISearchPresenter<V : ISearchView, I : ISearchInteractor> : IBasePresenter<V, I> {

}