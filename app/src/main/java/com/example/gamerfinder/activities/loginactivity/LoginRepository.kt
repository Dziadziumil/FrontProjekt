package com.example.gamerfinder.activities.loginactivity

import com.example.gamerfinder.R

class LoginRepository {

    val user: User? = null

    fun login(username: String, password: String): LoginResult {
        //TODO: api call
        return if(password == "1234567") {
            LoginResult(
                success = User(
                    "123",
                    username
                )
            )
        } else {
            LoginResult(error = R.string.login_failed)
        }
    }
}