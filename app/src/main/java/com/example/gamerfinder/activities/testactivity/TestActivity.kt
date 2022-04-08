package com.example.gamerfinder.activities.testactivity

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamerfinder.R
import com.example.gamerfinder.utils.*
import java.util.*


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.initProperties(this)
        setContentView(R.layout.activity_test)
    }

    fun buttonClick(v: View) {

        val get = HttpGet.TestsGet
        val event = HttpListener(object : Action {
            override fun onMessage(value: Any?) {
                Looper.prepare()
                val result = (value as ResponseModels.TestsClass).data?.firstOrNull()?.attributes
                val toast = Toast.makeText(
                    applicationContext,
                    "got a result of: ${result?.testNumber} and ${
                        result?.testColumn?.uppercase(
                            Locale.getDefault()
                        )
                    }",
                    Toast.LENGTH_SHORT
                )
                toast.show()
                println(value)
                Looper.loop()
            }
        })

        get.request(event)
    }
}

