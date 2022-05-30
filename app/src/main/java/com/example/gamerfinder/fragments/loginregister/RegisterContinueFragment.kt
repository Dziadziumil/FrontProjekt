package com.example.gamerfinder.fragments.loginregister

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.gamerfinder.databinding.FragmentRegisterContinueBinding
import com.example.gamerfinder.utils.*
import java.util.*

class RegisterContinueFragment : Fragment() {

    private var _binding: FragmentRegisterContinueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterContinueBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val namePattern = Regex("^[a-zA-Z]+\$", RegexOption.IGNORE_CASE)
    private var c = Calendar.getInstance()
    private var cYear = c.get(Calendar.YEAR)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            checkRegisterData(view)
        }
    }

    private fun checkRegisterData(view: View) {
        var isCorrect = true
        if (binding.personName.length() < 2 || !namePattern.containsMatchIn(binding.personName.text.toString())) {
            binding.personNameLayout.error = "Wrong user's Name."
            isCorrect = false
        }
        if (binding.personSurname.length() < 2 || !namePattern.containsMatchIn(binding.personSurname.text.toString())) {
            binding.personSurnameLayout.error = "Wrong user's Surname."
            isCorrect = false
        }
        if (cYear - binding.personBirth.year < 13) {
            binding.DateLayout.error = "You must be at least 13 years old."
            isCorrect = false
        }
        var gender: String? = null
        if (!binding.buttonFemale.isChecked && !binding.buttonMale.isChecked && !binding.buttonOther.isChecked) {
            binding.GenderLayout.error = "Please tell us your gender."
            isCorrect = false
        } else {
            when {
                binding.buttonFemale.isChecked -> {
                    gender = "female"
                }
                binding.buttonMale.isChecked -> {
                    gender = "male"
                }
                binding.buttonOther.isChecked -> {
                    gender = "other"
                }
            }
        }
        if (isCorrect) {
            val handler = Handler(Looper.getMainLooper())
            HttpPut.UpdateUser.apply {
                this.addListener(HttpListener(object : Action<Nothing> {
                    override fun onMessage(
                        isSuccess: Boolean,
                        value: Nothing?
                    ) {
                        if (isSuccess) {
                            handler.post {
                                val actionNo =
                                    RegisterContinueFragmentDirections.actionRegisterContinueFragmentToLoginFragment()
                                view.findNavController().navigate(actionNo)
                            }
                        } else {
                            println("register wasn't a success!")
                        }
                    }
                }))
            }.requestPost(
                RequestModels.UserData(
                    AccountService(requireContext()).getCurrentUserId().toInt(),
                    null,
                    binding.personName.text.toString(),
                    binding.personSurname.text.toString(),
                    "${
                        binding.personBirth.year.toString().padStart(4, '0')
                    }-${
                        binding.personBirth.month.toString().padStart(2, '0')
                    }-${binding.personBirth.dayOfMonth.toString().padStart(2, '0')}T00:00:00",
                    gender
                ),
                requireContext()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}