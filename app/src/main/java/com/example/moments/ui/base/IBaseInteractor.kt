package com.example.moments.ui.base

import com.example.moments.util.AppConstants

interface IBaseInteractor {
    fun isUserLoggedIn(): Boolean

    fun performUserLogout()
}