package com.example.gamerfinder.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.gamerfinder.R

class UserPreferences(context: Context) {

    private val applicationContext = context.applicationContext
    private val preferences: SharedPreferences = applicationContext.getSharedPreferences(
        R.string.preferences_file.toString(), Context.MODE_PRIVATE)

    val authToken: String?
        get() = preferences.getString("auth_token", null)

    fun saveAuthToken(token: String) {
        with(preferences.edit()) {
            putString("auth_token", token)
            apply()
        }
    }
}