package com.example.gamerfinder.utils

import android.accounts.Account
import android.content.Context
import kotlinx.serialization.*


import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.reflect.KClass

annotation class Api(val path: String)
annotation class UseId
annotation class UseToken

enum class ResponseCodes(val code: Int) {
    OK(200),
    BAD_REQUEST(400)
}


val MEDIA_TYPE_MARKDOWN = "application/json; charset=utf-8".toMediaType()
val regex = Regex("\"type\":\"[^\"]*\",")


sealed class ApiClient<Req : RequestModels.BaseModel, Rsp> {

    fun requestPost(req: Req?, context: Context) {
        privRequest(req, context)
    }

    fun addListener(event: HttpListener<Rsp>) {
        this.event = event
    }

    lateinit var event: HttpListener<Rsp>

    fun requestGet(context: Context) {
        privRequest(null, context)
    }


    val responseSerializer: KSerializer<*>

    @OptIn(InternalSerializationApi::class)
    constructor(responseClass: KClass<*>) {
        this.responseSerializer = responseClass.serializer()
    }

    @OptIn(InternalSerializationApi::class)
    constructor(responseSerializer: KSerializer<*>) {
        this.responseSerializer = responseSerializer
    }

    @OptIn(InternalSerializationApi::class)
    @Suppress("UNCHECKED_CAST")
    private fun privRequest(requestBody: Req? = null, context: Context) {
        val annotations = this.javaClass.annotations
        var apiUrl = (annotations.find { it is Api } as Api).path
        val accService = AccountService(context)
        annotations.find { it is UseId }?.let {
            apiUrl += accService.getCurrentUserId()
        }
        val useToken = annotations.any { it is UseToken }

        thread {
            val url = "https://${Configs.serverIp}/api/$apiUrl"
            println(url)

            val request = if (requestBody == null) {
                if (useToken) {
                    Request.Builder()
                        .url(url)
                        .header("Authorization", "Bearer ${accService.getCurrentAccountToken()}")
                        .build()
                } else {
                    Request.Builder()
                        .url(url)
                        .build()
                }
            } else {
                val json =
                    Json.encodeToString<RequestModels.BaseModel>(requestBody).replace(regex, "")
                println(json)
                if (useToken) {
                    Request.Builder()
                        .url(url)
                        .header("Authorization", "Bearer ${accService.getCurrentAccountToken()}")
                        .post(
                            json.toRequestBody(MEDIA_TYPE_MARKDOWN)
                        )
                        .build()
                } else {
                    Request.Builder()
                        .url(url)
                        .post(
                            json.toRequestBody(MEDIA_TYPE_MARKDOWN)
                        )
                        .build()
                }
            }
            println("request: $request")
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("exception happened ${e.message}")
                    event.listener.onMessage(false, null)

                }

                override fun onResponse(call: Call, response: Response) {
                    val result = try {
                        when (response.code) {
                            ResponseCodes.OK.code -> {
                                val rsp = response.body!!.string()
                                val json = Json {
                                    ignoreUnknownKeys = true
                                    isLenient = true
                                    prettyPrint = false
                                    allowStructuredMapKeys = true
                                }
                                println("got response: $rsp")
                                val jsonrsp = json.decodeFromString(responseSerializer, rsp)
                                jsonrsp as Rsp?
                            }
                            else -> {
                                println("got error: ${response.body!!.string()}")
                                null
                            }
                        }

                    } catch (e: Exception) {
                        println("IT DIDN'T WORK ${e.message}")
                        null
                    }
                    event.listener.onMessage(result != null, result)
                }
            }
            )

        }

    }
}
