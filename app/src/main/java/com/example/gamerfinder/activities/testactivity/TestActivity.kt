package com.example.gamerfinder.activities.testactivity

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.testactivity.Listener.Action
import com.example.gamerfinder.utils.Configs
import com.example.gamerfinder.utils.UserGet


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.initProperties(this)
        setContentView(R.layout.activity_test)
    }

    fun buttonClick(v: View) {

        val event = Listener().apply {
            this.setActionListener(object : Action {
                override fun invoke(value: String?) {
                    Looper.prepare()
                    val toast = Toast.makeText(baseContext, value, Toast.LENGTH_SHORT)
                    toast.show()
                    println(value)
                    Looper.loop()
                }
            })
        }

        UserGet().getRequest(event)
    }
}


class Listener {

    var listener: Action? = null

    fun setActionListener(listener: Action?) {
        this.listener = listener
    }

    interface Action {
        fun invoke(value: String?)
    }
}