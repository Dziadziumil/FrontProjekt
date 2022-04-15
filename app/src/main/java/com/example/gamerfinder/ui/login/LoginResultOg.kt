package com.example.gamerfinder.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResultOg(
        val success: LoggedInUserView? = null,
        val error: Int? = null
)