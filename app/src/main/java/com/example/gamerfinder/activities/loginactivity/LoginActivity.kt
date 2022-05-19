package com.example.gamerfinder.activities.loginactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.gamerfinder.databinding.ActivityLoginBinding
import com.example.gamerfinder.activities.profile.MyProfileActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: LoginViewModel by viewModels()

        val username = binding.usernameEditText
        val password = binding.passwordEditText

        binding.loginButton.setOnClickListener {
            viewModel.login(username.text.toString(), password.text.toString())
        }

        viewModel.loginStatus.observe(this, Observer {
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
        })

        viewModel.loginResult.observe(this, Observer {
            if (it.success != null) {
                Snackbar.make(binding.loginButton, it.success.username, Snackbar.LENGTH_SHORT)
                    .show()
                handleLoginSuccess()
            }
            if (it.error != null) {
                Snackbar.make(binding.loginButton, getString(it.error), Snackbar.LENGTH_SHORT)
                    .show()
                //TODO: handle error
            }
        })
    }

    fun handleLoginSuccess() {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent)
    }

}