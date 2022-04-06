package com.example.gamerfinder.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.concurrent.thread


private val client = OkHttpClient()


fun run() {
    thread {
        val request = Request.Builder()
            .url("http://10.0.1.30:1337/api/tests")
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            println(response.body!!.string())
        }
    }
}