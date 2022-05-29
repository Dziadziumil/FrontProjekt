package com.example.gamerfinder.utils

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class AccountService() : Service() {

    fun test() {
    }

    fun addAccount(userName: String, passwordHash: String) {
        AccountManager.get(
            applicationContext
        ).let {
            it.addAccountExplicitly(
                Account(userName, "gamerFinder.user"),
                passwordHash,
                Bundle.EMPTY
            )
            println(it.accounts)
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}