package com.example.gamerfinder.utils

import com.example.gamerfinder.utils.Configs
import com.example.gamerfinder.utils.HttpListener
import kotlinx.serialization.*


import com.example.gamerfinder.R
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.io.Serializable
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.reflect.KClass

annotation class Api(val path: String)

enum class ResponseCodes(val code: Int) {
    OK(200),
    BAD_REQUEST(400)
}


val MEDIA_TYPE_MARKDOWN = "application/json; charset=utf-8".toMediaType()
val regex = Regex("\"type\":\"[^\"]*\",")


sealed class ApiClient<Req : RequestModels.BaseModel, Rsp> {

    fun requestPost(req: Req?) {
        privrequest(req)
    }

    fun addListener(event: HttpListener<Rsp>) {
        this.event = event
    }

    lateinit var event: HttpListener<Rsp>

    fun requestGet() {
        privrequest(null)
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
    private fun privrequest(requestBody: Req? = null) {
        val apiUrl = (this.javaClass.annotations.find { it is Api } as Api).path
        thread {
            val url = "https://${Configs.serverIp}/api/$apiUrl"
            println(url)

            val request = if (requestBody == null) {
                Request.Builder()
                    .url(url)
                    .build()
            } else {
                val json =
                    Json.encodeToString<RequestModels.BaseModel>(requestBody).replace(regex, "")
                println(json)
                Request.Builder()
                    .url(url)
                    .post(
                        json.toRequestBody(MEDIA_TYPE_MARKDOWN)
                    )
                    .build()
            }
            println("request: $request")

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("exception happened ${e.message}")
                    event.listener.onMessage(false, null)

                }

                override fun onResponse(call: Call, response: Response) {
                    try {
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
                                event.listener.onMessage(true, jsonrsp as Rsp?)
                            }
                            else -> {
                                println("got error: ${response.body!!.string()}")
                                event.listener.onMessage(false, null)
                            }
                        }

                    } catch (e: Exception) {
                        event.listener.onMessage(false, null)
                        println("IT DIDN'T WORK ${e.message}")
                    }
                }
            }
            )

        }

    }
}
