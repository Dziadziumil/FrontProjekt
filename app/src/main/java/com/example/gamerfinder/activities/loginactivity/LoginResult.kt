package com.example.gamerfinder.activities.loginactivity

sealed class LoginResult<out T : Any> {

    data class Success<out T : Any>(val value: T) : LoginResult<T>()
    data class Error(val error: String) : LoginResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success(value=$value)"
            is Error -> "Error(message=$error)"
        }
    }
}