package com.example.gamerfinder.activities.loginactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.gamerfinder.databinding.ActivityLoginBinding
import com.example.gamerfinder.activities.profile.MyProfileActivity
import com.example.gamerfinder.activities.registeractivity.RegisterActivity
import com.example.gamerfinder.utils.*
import com.google.android.material.snackbar.Snackbar
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.initProperties(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: LoginViewModel by viewModels()

        val username = binding.usernameEditText
        val password = binding.passwordEditText

        binding.loginButton.setOnClickListener {
            viewModel.login(username.text.toString(), password.text.toString())
        }

        binding.testButton.setOnClickListener {
            val get = HttpGet.UsersGet
            val event = HttpListener(object : Action {
                override fun onMessage(value: Any?) {
                    Looper.prepare()
                    val result =
                        value as ResponseModels.UsersClass
                    val toast = Toast.makeText(
                        applicationContext,
                        "got a result of: $result",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                    println(value)
                    Looper.loop()
                }
            })
            println("sending request")
            get.request(event)
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

        binding.noAccountButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    fun handleLoginSuccess() {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent)
    }

}