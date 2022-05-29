package com.example.gamerfinder.utils

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class AccountService(val context: Context) : Service() {


    fun addAccount(id: Int, userName: String, passwordHash: String) {
        val account = Account(userName, "gamerFinder.user")
        AccountManager.get(
            context
        ).let {
            it.addAccountExplicitly(
                account,
                passwordHash,
                null
            )
            it.setUserData(account, "id", id.toString())
            println(it.accounts)
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}