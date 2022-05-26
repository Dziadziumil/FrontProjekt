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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailPattern = Regex("\\w+@\\w+[.]\\w+", RegexOption.IGNORE_CASE)

        binding.button.setOnClickListener {
            when {
                binding.username.text.isNullOrEmpty() -> {
                    Toast.makeText(context,"Please tell us how we should call you.", Toast.LENGTH_LONG).show()
                }
                binding.username.length() < 4 -> {
                    binding.usernameLayout.error = "Nickname is too short."
                }
                !emailPattern.containsMatchIn(binding.personEmail.text.toString()) -> {
                    binding.personEmailLayout.error = "Please enter correct email address."
                }
                binding.password.length() < 6 -> {
                    binding.passwordLayout.error = "Password is too short."
                }
                binding.confirmPassword.length() < 6 -> {
                    binding.confirmPasswordLayout.error = "Please retype your password."
                }
                binding.password.text.toString() != binding.confirmPassword.text.toString() -> {
                    binding.confirmPasswordLayout.error = "Wrong password's retype."
                }
                !binding.termsOfService.isChecked -> {
                    Toast.makeText(context, "Please accept the rules!", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val action = RegisterFragmentDirections.actionSignupFragmentToRegisterDecisionFragment()
                    view.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}