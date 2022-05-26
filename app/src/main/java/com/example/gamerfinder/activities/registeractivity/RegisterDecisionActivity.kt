package com.example.gamerfinder.activities.registeractivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamerfinder.databinding.ActivityRegisterDecisionBinding
import com.example.gamerfinder.ui.login.LoginActivity

class RegisterDecisionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterDecisionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register_decision)
        binding = ActivityRegisterDecisionBinding.inflate(layoutInflater)

        binding.buttonNo.setOnClickListener {
            val ansNo = Intent(this, LoginActivity::class.java)
            startActivity(ansNo)
        }
        binding.buttonYes.setOnClickListener {
            val ansYes = Intent(this, RegisterContinueActivity::class.java)
            startActivity(ansYes)
        }
    }
}