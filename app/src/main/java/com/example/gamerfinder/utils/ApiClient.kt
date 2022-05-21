package com.example.gamerfinder.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession
import kotlin.concurrent.thread


annotation class Api(val path: String)

open class ApiClient(val serializer: KSerializer<*>) {

    fun request(event: HttpListener) {
        runCatching {
            val apiUrl = (this.javaClass.annotations.find { it is Api } as Api).path
            thread {
                val url = "https://${Configs.serverIp}/api/$apiUrl"
                println(url)
                val request = Request.Builder()
                    .url(url)
                    .build()
                val client = OkHttpClient()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("${e.message}")
                        throw e
                    }

                    override fun onResponse(call: Call, response: Response) {
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
                )
            }
        }.onFailure {
            println("IT DIDN'T WORK ${it.message}")
        }
    }
}
