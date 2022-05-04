package com.example.moments.ui.main.search

import com.example.moments.data.model.Post
import com.example.moments.data.model.RetrievedPost
import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Observable
import io.reactivex.Single

interface ISearchView : IBaseView {
    fun onSearchResultCallback(listUser: List<User>)
    fun updateGrid(listPosts:List<Post>)
}

interface ISearchInteractor : IBaseInteractor {
    fun doSearchUserByUsername(query: String) : Observable<List<User>>
    fun queryAllPost(): Single<List<Post>>
}

interface ISearchPresenter<V : ISearchView, I : ISearchInteractor> : IBasePresenter<V, I> {
    fun onSearchDispatch(query: String)
    fun onQueryAllPost()
}