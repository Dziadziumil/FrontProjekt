package com.example.gamerfinder.activities.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gamerfinder.R

class MyProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_my_profile)
    }

    fun buttonClickEdit(v: View)
    {
        val activity2Intent = Intent(applicationContext, MyProfileEditActivity::class.java)
        startActivity(activity2Intent)
    }
}