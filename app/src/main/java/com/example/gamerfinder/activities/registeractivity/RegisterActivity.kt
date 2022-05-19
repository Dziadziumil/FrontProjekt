package com.example.gamerfinder.activities.registeractivity

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
        val namePattern = Regex("^[a-zA-Z]+\$", RegexOption.IGNORE_CASE)
        var c = Calendar.getInstance()
        var cYear = c.get(Calendar.YEAR)

        binding.button.setOnClickListener {
            when {
                binding.personName.length() < 2 || !namePattern.containsMatchIn(binding.personName.text.toString()) -> {
                    binding.personNameLayout.error = "Wrong user's Name."
                    //Toast.makeText(this, "Wrong user's Name.", Toast.LENGTH_LONG).show()
                }
                binding.personSurname.length() < 2 || !namePattern.containsMatchIn(binding.personSurname.text.toString()) -> {
                    Toast.makeText(this, "Wrong user's Surname.", Toast.LENGTH_LONG).show()
                }
                binding.username.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        this,
                        "Please tell us how we should call you.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                binding.username.length() < 4 -> {
                    Toast.makeText(this, "Nickname is too short.", Toast.LENGTH_LONG).show()
                }
                !emailPattern.containsMatchIn(binding.personEmail.text.toString()) -> {
                    Toast.makeText(this, "Please enter correct email address.", Toast.LENGTH_LONG)
                        .show()
                }
                cYear - binding.personBirth.year < 13 -> {
                    Toast.makeText(this, "You must be at least 13 years old.", Toast.LENGTH_LONG)
                        .show()
                }
                binding.password.length() < 6 -> {
                    Toast.makeText(this, "Password is too short.", Toast.LENGTH_LONG).show()
                }
                binding.confirmPassword.length() < 6 -> {
                    Toast.makeText(this, "Please retype your password.", Toast.LENGTH_LONG).show()
                }
                binding.password.text.toString() != binding.confirmPassword.text.toString() -> {
                    Toast.makeText(this, "Wrong password's retype.", Toast.LENGTH_LONG).show()
                }
                !binding.buttonFemale.isChecked && !binding.buttonMale.isChecked && !binding.buttonOther.isChecked -> {
                    Toast.makeText(this, "Please tell us your gender.", Toast.LENGTH_LONG).show()
                }
                !binding.termsOfService.isChecked -> {
                    Toast.makeText(this, "Please accept the rules!", Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.button.text = "Registered!"
                }
            }
        }
    }
}