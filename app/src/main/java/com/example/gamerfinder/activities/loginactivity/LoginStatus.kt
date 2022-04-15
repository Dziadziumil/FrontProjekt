package com.example.gamerfinder.activities.loginactivity

data class LoginStatus(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)