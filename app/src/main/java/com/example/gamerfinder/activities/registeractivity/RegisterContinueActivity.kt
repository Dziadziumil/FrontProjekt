package com.example.gamerfinder.activities.registeractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.gamerfinder.databinding.ActivityRegisterContinueBinding
import org.w3c.dom.Text
import java.util.*

class RegisterContinueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterContinueBinding
    private lateinit var personBirth: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register_continue)
        binding = ActivityRegisterContinueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.text = "Confirm"

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
                cYear - binding.personBirth.year < 13 -> {
                    Toast.makeText(this, "You must be at least 13 years old.", Toast.LENGTH_LONG)
                        .show()
                }
                !binding.buttonFemale.isChecked && !binding.buttonMale.isChecked && !binding.buttonOther.isChecked -> {
                    Toast.makeText(this, "Please tell us your gender.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.button.text = "Confirmed!"
                }
            }
        }
    }
}