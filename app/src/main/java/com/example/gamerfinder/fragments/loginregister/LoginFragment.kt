package com.example.gamerfinder.fragments.loginregister

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gamerfinder.HomeActivity
import com.example.gamerfinder.activities.loginactivity.LoginViewModel
import com.example.gamerfinder.databinding.FragmentLoginBinding
import com.example.gamerfinder.utils.*
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.initProperties(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = binding.usernameEditText
        val password = binding.passwordEditText

        binding.loginButton.setOnClickListener {
            viewModel.login(username.text.toString(), password.text.toString())
        }


        /*binding.testButton.setOnClickListener {
            val get = HttpGet.UsersGet
            val event = HttpListener(object : Action<List<ResponseModels.UserFull>> {
                override fun onMessage(isSuccess: Boolean, value: List<ResponseModels.UserFull>?) {
                    Looper.prepare()
                    val result =
                        value as List<ResponseModels.UserFull>
                    val toast = Toast.makeText(
                        context,
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

        binding.testButton.setOnClickListener {
            val post = HttpPost.AuthenticatePost
            post.addListener(HttpListener(object : Action<ResponseModels.AuthResponse> {
                override fun onMessage(isSuccess: Boolean, value: ResponseModels.AuthResponse?) {
                    Looper.prepare()
                    val toast = Toast.makeText(
                        context,
                        "got a result $isSuccess of: $value",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                    println(value)
                    Looper.loop()
                }
            }))

            println("sending request")
            post.requestPost(RequestModels.AuthRequest("string", "string"))
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
                handleLoginSuccess()
            }
            if (it.error != null) {
                Snackbar.make(binding.loginButton, getString(it.error), Snackbar.LENGTH_SHORT)
                    .show()
                //TODO: handle error
            }
        }

        binding.gotoHomeActivity.setOnClickListener {
            handleLoginSuccess()
        }
    }

    private fun handleLoginSuccess() {
        val intent = Intent(context, HomeActivity::class.java)
        context?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}