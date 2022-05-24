package com.example.gamerfinder.activities.registeractivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.gamerfinder.databinding.ActivityRegisterBinding
import org.w3c.dom.Text
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var personBirth: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.text = "Register"

        val emailPattern = Regex("\\w+@\\w+[.]\\w+", RegexOption.IGNORE_CASE)

        binding.button.setOnClickListener {
            when {
                binding.username.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        this,
                        "Please tell us how we should call you.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                binding.username.length() < 4 -> {
                    binding.usernameLayout.error = "Nickname is too short."
                    //Toast.makeText(this, "Nickname is too short.", Toast.LENGTH_LONG).show()
                }
                !emailPattern.containsMatchIn(binding.personEmail.text.toString()) -> {
                    binding.personEmailLayout.error = "Please enter correct email address."
                    //Toast.makeText(this, "Please enter correct email address.", Toast.LENGTH_LONG).show()
                }
                binding.password.length() < 6 -> {
                    binding.passwordLayout.error = "Password is too short."
                    //Toast.makeText(this, "Password is too short.", Toast.LENGTH_LONG).show()
                }
                binding.confirmPassword.length() < 6 -> {
                    binding.confirmPasswordLayout.error = "Please retype your password."
                    //Toast.makeText(this, "Please retype your password.", Toast.LENGTH_LONG).show()
                }
                binding.password.text.toString() != binding.confirmPassword.text.toString() -> {
                    binding.confirmPasswordLayout.error = "Wrong password's retype."
                    //Toast.makeText(this, "Wrong password's retype.", Toast.LENGTH_LONG).show()
                }
                !binding.termsOfService.isChecked -> {
                    Toast.makeText(this, "Please accept the rules!", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val ansDec = Intent(this, RegisterDecisionActivity::class.java)
                    startActivity(ansDec)
                }
            }
        }
    }
}