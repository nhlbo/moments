package com.example.moments.ui.base

interface IBaseView {
    fun showProgress()

    fun hideProgress()

    fun showCustomToastMessage(message: String)
}