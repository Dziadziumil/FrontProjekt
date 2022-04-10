package com.example.gamerfinder.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.concurrent.thread


annotation class Api(val path: String)

open class ApiClient(val serializer: KSerializer<*>) {

    fun request(event: HttpListener) {
        runCatching {
            val apiUrl = (this.javaClass.annotations.find { it is Api } as Api).path
            thread {
                val request = Request.Builder()
                    .url("http://${Configs.serverIp}:${Configs.serverPort}/api/${apiUrl}")
                    .build()
                OkHttpClient().newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    val rsp = response.body!!.string()
                    val json = Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = false
                        allowStructuredMapKeys = true
                    }

                    val jsonrsp = json.decodeFromString(serializer, rsp)
                    event.listener.onMessage(jsonrsp)


                }
            }
        }.onFailure {
            println("IT DIDN'T WORK ${it.message}")
        }
    }
}
