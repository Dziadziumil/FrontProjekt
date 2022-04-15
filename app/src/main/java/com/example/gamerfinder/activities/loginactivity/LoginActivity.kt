package com.example.gamerfinder.activities.loginactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.gamerfinder.R
import com.example.gamerfinder.databinding.ActivityLoginBinding
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
            if(it.usernameError != null) {
                username.error = getString(it.usernameError)
            }
            if(it.passwordError != null){
                password.error = getString(it.passwordError)
            }
        })

        viewModel.loginResult.observe(this, Observer {
            if(it.success != null){
                Snackbar.make(binding.loginButton, it.success.username, Snackbar.LENGTH_SHORT).show()
                //TODO: login
            }
            if(it.error != null) {
                Snackbar.make(binding.loginButton, getString(it.error), Snackbar.LENGTH_SHORT).show()
                //TODO: handle error
            }
        })
    }
}