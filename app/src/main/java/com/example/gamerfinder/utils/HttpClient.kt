package com.example.gamerfinder.utils

import android.graphics.Bitmap
import com.example.gamerfinder.activities.testactivity.Listener
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.concurrent.thread


private val client = OkHttpClient()

annotation class Api(val path: String)

@Api("tests")
class UserGet : ApiClient()


open class ApiClient {
    fun getRequest(event: Listener) {
        val apiUrl = (this.javaClass.annotations.find { it is Api } as Api).path
        thread {
            val request = Request.Builder()
                .url("http://${Configs.serverIp}:${Configs.serverPort}/api/${apiUrl}")
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                for ((name, value) in response.headers) {
                    println("$name: $value")
                }

                val rsp = response.body!!.string()
                event.listener?.let {
                    it.invoke(rsp)
                }

            }
        }
    }
}
