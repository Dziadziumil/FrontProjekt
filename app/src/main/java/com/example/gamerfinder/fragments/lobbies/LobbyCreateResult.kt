package com.example.gamerfinder.fragments.lobbies

import com.example.gamerfinder.activities.loginactivity.LoginResult

sealed class LobbyCreateResult<out T : Any> {
    data class Success<out T : Any>(val value: T?) : LobbyCreateResult<T>()
    data class Error(val error: String) : LobbyCreateResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success(value=$value)"
            is Error -> "Error(message=$error)"
        }
    }
}