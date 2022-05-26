package com.example.gamerfinder.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gamerfinder.databinding.FragmentRegisterContinueBinding
import java.util.*

class RegisterContinueFragment : Fragment() {

    private var _binding: FragmentRegisterContinueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterContinueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    binding.personSurnameLayout.error = "Wrong user's Surname."
                }
                cYear - binding.personBirth.year < 13 -> {
                    Toast.makeText(context, "You must be at least 13 years old.", Toast.LENGTH_LONG).show()
                }
                !binding.buttonFemale.isChecked && !binding.buttonMale.isChecked && !binding.buttonOther.isChecked -> {
                    Toast.makeText(context, "Please tell us your gender.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.button.text = "Confirmed!"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}