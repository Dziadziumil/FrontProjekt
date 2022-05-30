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


    fun addAccount(id: Int, userName: String) {
        AccountManager.get(
            context
        ).let {
            it.getAccountsByType("gamerFinder.user").let { arr ->
                when (arr.size) {
                    0 -> {
                        val account = Account(userName, "gamerFinder.user")
                        it.addAccountExplicitly(
                            account,
                            null,
                            null
                        )
                        it.setUserData(account, "id", id.toString())
                    }
                    1 -> {
                        it.renameAccount(arr[0], userName, null, null)
                        it.setUserData(arr[0], "id", it.toString())
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