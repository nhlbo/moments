package com.example.moments.ui.base

interface IBaseInteractor {
    fun isUserLoggedIn(): Boolean

    fun performUserLogout()
}