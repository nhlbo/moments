package com.example.moments.util

object AppConstants {
    internal val PREF_NAME = "momments_pref"
    internal val EMPTY_EMAIL_ERROR = 1001
    internal val INVALID_EMAIL_ERROR = 1002
    internal val EMPTY_PASSWORD_ERROR = 1003
    internal val LOGIN_FAILURE = 1004
    internal val EMPTY_USERNAME_ERROR = 1005
    internal val CONFIRM_PASSWORD_NOT_MATCH_ERROR = 1006
    internal val INVALID_PASSWORD_ERROR = 1007
    internal val INVALID_RESET_PASSWORD_CODE_ERROR = 1008
    internal val NULL_INDEX = -1L

    enum class LoggedInMode constructor(val type: Int) {
        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_EMAIL(1),
        LOGGED_IN_MODE_GOOGLE(2)
    }
}