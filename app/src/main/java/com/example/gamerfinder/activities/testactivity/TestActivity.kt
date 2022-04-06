package com.example.gamerfinder.activities.testactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gamerfinder.R
import com.example.gamerfinder.utils.run

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun buttonClick(v: View) {
        run()
    }


}