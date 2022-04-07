package com.example.gamerfinder.utils

import android.content.Context
import android.content.res.AssetManager
import java.util.*


object Configs {
    enum class EKey(val value: String) {
        SERVER_IP("server_ip"),
        SERVER_PORT("server_port")
    }

    private var properties: Properties = Properties()

    fun initProperties(context: Context) {
        val assetManager: AssetManager = context.assets
        val inputStream = assetManager.open("configuration.properties")
        properties.load(inputStream)
    }

    operator fun get(key: EKey): String? {
        return properties.getProperty(key.value)
    }

    val serverIp: String by lazy {
        runCatching {
            return@lazy get(EKey.SERVER_IP)?.trim() ?: "127.0.0.1"
        }
        "127.0.0.1"
    }
    val serverPort: String by lazy {
        runCatching {
            return@lazy get(EKey.SERVER_PORT)?.trim() ?: "1337"
        }
        "1337"
    }

}


