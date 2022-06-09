package com.example.gamerfinder.fragments.loginregister

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.gamerfinder.databinding.FragmentRegisterBinding
import com.example.gamerfinder.utils.*
import java.security.MessageDigest

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

    private val emailPattern = Regex("\\w+@\\w+[.]\\w+", RegexOption.IGNORE_CASE)

    fun getHash(password: String): String {
        return MessageDigest.getInstance("SHA-256").digest((password).toByteArray())
            .joinToString("") { "%02x".format(it) }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.termsOfService.setOnCheckedChangeListener { buttonView, isChecked ->
            hideKeyboard()
        }

        binding.button.setOnClickListener {
            val isCorrect = checkRegisterData(view)
            if (isCorrect) {
                val email = binding.personEmail.text.toString()
                val username = binding.username.text.toString()
                val passwordHash = getHash(binding.password.text.toString())
                val handler = Handler(Looper.getMainLooper())
                HttpPost.RegisterPost.apply {
                    this.addListener(HttpListener(object : Action<ResponseModels.IdToken> {
                        override fun onMessage(
                            isSuccess: Boolean,
                            value: ResponseModels.IdToken?
                        ) {
                            if (isSuccess) {
                                AccountService(requireContext()).addAccount(
                                    value!!.id!!
                                )

                                val action =
                                    RegisterFragmentDirections.actionSignupFragmentToRegisterDecisionFragment()
                                handler.post {
                                    view.findNavController().navigate(action)
                                }
                            } else {
                                //println("register wasn't a success!")
                                var dialog = CustomDialogFragment()
                                dialog.show(childFragmentManager, "customDialog")
                            }
                        }
                    }))
                }.requestPost(
                    RequestModels.RegisterRequest(
                        email,
                        username,
                        passwordHash
                    ),
                    requireContext()
                )

            }
        }
    }

    private fun checkRegisterData(view: View): Boolean {
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
            isCorrect = false
        }
        return isCorrect
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}