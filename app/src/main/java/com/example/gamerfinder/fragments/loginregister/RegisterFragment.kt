package com.example.gamerfinder.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.gamerfinder.databinding.FragmentRegisterBinding

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

    val emailPattern = Regex("\\w+@\\w+[.]\\w+", RegexOption.IGNORE_CASE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button.setOnClickListener {
            checkRegisterData(view)
        }
    }

    private fun checkRegisterData(view: View) {
        /* binding.username.text.isNullOrEmpty() -> {
                Toast.makeText(context,"Please tell us how we should call you.", Toast.LENGTH_LONG).show()
            }*/
        var isCorrect = true
        if (binding.username.length() < 4) {
            binding.usernameLayout.error = "Nickname is too short."
            isCorrect = false
        }
        if (!emailPattern.containsMatchIn(binding.personEmail.text.toString())) {
            binding.personEmailLayout.error = "Please enter correct email address."
            isCorrect = false
        }
        if (binding.password.length() < 6) {
            binding.passwordLayout.error = "Password is too short."
            isCorrect = false
        }
        if (binding.confirmPassword.length() < 6) {
            binding.confirmPasswordLayout.error = "Please retype your password."
            isCorrect = false
        }
        if (binding.password.text.toString() != binding.confirmPassword.text.toString()) {
            binding.confirmPasswordLayout.error = "Wrong password's retype."
            isCorrect = false
        }
        if (!binding.termsOfService.isChecked) {
            binding.termsOfService.error = "Please accept the rules!"
            binding.termsOfService.requestFocus()
            // Toast.makeText(context, , Toast.LENGTH_LONG).show()
            isCorrect = false
        }
        if (isCorrect) {
            val action =
                RegisterFragmentDirections.actionSignupFragmentToRegisterDecisionFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}