package com.example.moments.ui.vuforia

import com.example.moments.data.model.User
import com.example.moments.ui.base.IBaseInteractor
import com.example.moments.ui.base.IBasePresenter
import com.example.moments.ui.base.IBaseView
import io.reactivex.Single

interface IVuforiaActivityView : IBaseView {
    fun getUserByVuforiaId(user: User)
}

interface IVuforiaActivityPresenter<V : IVuforiaActivityView, I : IVuforiaActivityInteractor> :
    IBasePresenter<V, I> {
    fun onPerformQueryUserByVuforiaId(vuforiaId: String)
}

interface IVuforiaActivityInteractor : IBaseInteractor {
    fun doPerformQueryUserByVuforiaId(vuforiaId: String): Single<User>
}
