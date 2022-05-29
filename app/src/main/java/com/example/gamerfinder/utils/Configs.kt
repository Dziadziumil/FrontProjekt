package com.example.gamerfinder.utils

import android.content.Context
import android.content.res.AssetManager
import java.util.*


object Configs {
    enum class EKey(val value: String) {
        SERVER_IP("server_ip")
    }

    private var properties: Properties = Properties()
    private var isInitialized: Boolean = false
    fun initProperties(context: Context) {
        isInitialized = true
        val assetManager: AssetManager = context.assets
        val inputStream = assetManager.open("configuration.properties")
        properties.load(inputStream)
    }

    operator fun get(key: EKey): String? {
        if(!isInitialized) throw ExceptionInInitializerError("Configs were not initialized!!!")
        return properties.getProperty(key.value)
    }

    val serverIp: String by lazy {
        runCatching {
            return@lazy get(EKey.SERVER_IP)?.trim() ?: "127.0.0.1"
        }.onFailure {
            println("Couldn't get configuration properties, going with default: ${it.message}")
        }
        "127.0.0.1"
    }

}


