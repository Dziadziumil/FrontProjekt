package com.example.gamerfinder.fragments.loginregister

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gamerfinder.HomeActivity
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.activities.loginactivity.LoginViewModel
import com.example.gamerfinder.utils.UserPreferences
import com.example.gamerfinder.databinding.FragmentLoginBinding
import com.example.gamerfinder.ui.login.afterTextChanged
import com.example.gamerfinder.utils.*

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreferences: UserPreferences

    private var _authToken: String? = null
    private val authToken get() = _authToken

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configs.initProperties(requireContext())
        userPreferences = UserPreferences(requireContext())
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

        username.afterTextChanged {
            binding.usernameLayout.error = null
            binding.passwordLayout.error = null
        }
        password.afterTextChanged {
            binding.usernameLayout.error = null
            binding.passwordLayout.error = null
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(username.text.toString(), password.text.toString())
        }


        //_authToken = userPreferences.authToken
        //if(authToken != null)
            //Toast.makeText(context, authToken, Toast.LENGTH_SHORT).show()

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
            when(it) {
                is LoginResult.Success -> {
                    userPreferences.saveAuthToken(it.value.token!!)
                    handleLoginSuccess()
                }
                is LoginResult.Error -> {
                    binding.usernameLayout.error = getString(R.string.login_error)
                    binding.passwordLayout.error = getString(R.string.login_error)
                }
            }
        }

        binding.gotoHomeActivity.setOnClickListener {
            handleLoginSuccess()
        }
    }

    private fun handleLoginSuccess() {
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}