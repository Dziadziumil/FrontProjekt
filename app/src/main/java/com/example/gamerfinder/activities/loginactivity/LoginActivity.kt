package com.example.gamerfinder.activities.loginactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.gamerfinder.activities.profile.MyProfileActivity
import com.example.gamerfinder.activities.registeractivity.RegisterActivity
import com.example.gamerfinder.databinding.ActivityLoginBinding
import com.example.gamerfinder.utils.*
import com.google.android.material.snackbar.Snackbar

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
            viewModel.login(username.text.toString(), password.text.toString(),applicationContext)
        }
        binding.noAccountButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


      /*  binding.testButton.setOnClickListener {
            val get = HttpGet.UsersGet

            val event = HttpListener(object : Action<List<ResponseModels.UserFull>> {
                override fun onMessage(isSuccess: Boolean, value: List<ResponseModels.UserFull>?) {
                    Looper.prepare()
                    val result =
                        value as List<ResponseModels.UserFull>
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
        }*/

        viewModel.loginStatus.observe(this) {
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

        viewModel.loginResult.observe(this) {
            when (it) {
                is LoginResult.Success -> {
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                }
                is LoginResult.Error -> {
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun handleLoginSuccess() {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent)
    }


}