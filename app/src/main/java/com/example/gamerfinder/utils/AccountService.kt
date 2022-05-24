package com.example.gamerfinder.utils

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class AccountService(val context: Context) : Service() {

    fun test() {
        AccountManager.get(context).let {
            it.addAccountExplicitly(
                Account("1", "gamerFinder.user"),
                "1234",
                Bundle.EMPTY
            )
            println("")
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}