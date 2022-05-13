package com.example.gamerfinder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gamerfinder.activities.loginactivity.LoginViewModel
import com.example.gamerfinder.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = binding.usernameEditText
        val password = binding.passwordEditText

        binding.loginButton.setOnClickListener {
            viewModel.login(username.text.toString(), password.text.toString())
            //Toast.makeText(context, "login button", Toast.LENGTH_LONG).show()
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) {
            if (it.usernameError != null) {
                binding.usernameLayout.error = getString(it.usernameError)
            }
            if (it.passwordError != null) {
                binding.passwordLayout.error = getString(it.passwordError)
            }
            if (it.isDataValid) {
                binding.passwordLayout.error = null
                binding.usernameLayout.error = null
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it.success != null) {
                Snackbar.make(binding.loginButton, it.success.username, Snackbar.LENGTH_SHORT)
                    .show()
                //TODO: login
            }
            if (it.error != null) {
                Snackbar.make(binding.loginButton, getString(it.error), Snackbar.LENGTH_SHORT)
                    .show()
                //TODO: handle error
            }
        }

        binding.gotoHomeActivity.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}