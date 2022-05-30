package com.example.gamerfinder.utils

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.RequiresApi

class AccountService(val context: Context) : Service() {


    fun getCurrentUserId(): String {
        AccountManager.get(context).let {
            it.getAccountsByType("gamerFinder.user").let { arr ->
                if (arr.size == 1) {
                    return arr[0].name
                } else {
                    throw IndexOutOfBoundsException("There was no active or too much users!")
                }
            }
        }
    }

    fun setCurrentAccountToken(token: String) {
        AccountManager.get(context).let {
            it.getAccountsByType("gamerFinder.user").let { arr ->
                if (arr.size == 1) {
                    it.setAuthToken(arr[0], "OAuth2", token)

                } else {
                    throw IndexOutOfBoundsException("There was no active or too much users!")
                }
            }
        }
    }

    fun getCurrentAccountToken(): String {
        AccountManager.get(context).let {
            it.getAccountsByType("gamerFinder.user").let { arr ->
                if (arr.size == 1) {
                    return it.peekAuthToken(arr[0], "OAuth2")

                } else {
                    throw IndexOutOfBoundsException("There was no active or too much users!")
                }
            }
        }
    }

    fun addAccount(id: Int) {
        AccountManager.get(
            context
        ).let {
            it.getAccountsByType("gamerFinder.user").let { arr ->
                when (arr.size) {
                    0 -> {
                        val account = Account(id.toString(), "gamerFinder.user")
                        it.addAccountExplicitly(
                            account,
                            null,
                            null
                        )
                        //it.setUserData(account, "id", id.toString())
                    }
                    1 -> {
                        val newName = id.toString()
                        if(arr[0].name!=newName)
                        it.renameAccount(arr[0], newName, null, null)
                        // it.setUserData(arr[0], "id", it.toString())
                    }
                    else -> {
                        throw IndexOutOfBoundsException("THERE WERE MORE THAN 1 USER!")
                    }
                }
                println(it.getAccountsByType("gamerFinder.user").map { it.name })
            }


        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}