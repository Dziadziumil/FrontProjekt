package com.example.gamerfinder.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gamerfinder.databinding.FragmentRegisterBinding
import java.util.*

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    Toast.makeText(context, "Wrong user's Surname.", Toast.LENGTH_LONG).show()
                }
                binding.username.text.isNullOrEmpty() -> {
                    Toast.makeText(
                        context,
                        "Please tell us how we should call you.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                binding.username.length() < 4 -> {
                    Toast.makeText(context, "Nickname is too short.", Toast.LENGTH_LONG).show()
                }
                !emailPattern.containsMatchIn(binding.personEmail.text.toString()) -> {
                    Toast.makeText(context, "Please enter correct email address.", Toast.LENGTH_LONG)
                        .show()
                }
                cYear - binding.personBirth.year < 13 -> {
                    Toast.makeText(context, "You must be at least 13 years old.", Toast.LENGTH_LONG)
                        .show()
                }
                binding.password.length() < 6 -> {
                    Toast.makeText(context, "Password is too short.", Toast.LENGTH_LONG).show()
                }
                binding.confirmPassword.length() < 6 -> {
                    Toast.makeText(context, "Please retype your password.", Toast.LENGTH_LONG).show()
                }
                binding.password.text.toString() != binding.confirmPassword.text.toString() -> {
                    Toast.makeText(context, "Wrong password's retype.", Toast.LENGTH_LONG).show()
                }
                !binding.buttonFemale.isChecked && !binding.buttonMale.isChecked && !binding.buttonOther.isChecked -> {
                    Toast.makeText(context, "Please tell us your gender.", Toast.LENGTH_LONG).show()
                }
                !binding.termsOfService.isChecked -> {
                    Toast.makeText(context, "Please accept the rules!", Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.button.text = "Registered!"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}