package com.example.gamerfinder.fragments.profile

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.databinding.FragmentMyProfileEditBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class MyProfileEditFragment : Fragment() {

    private var _binding: FragmentMyProfileEditBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: ProfileViewModel by activityViewModels()

    private val namePattern = Regex("^[a-zA-Z]+\$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = binding.usernameEditText
        val firstName = binding.firstnameEditText
        val secondName = binding.secondnameEditText
        val email = binding.emailEditText


        sharedViewModel.user.observe(viewLifecycleOwner) {
            binding.usernameEditText.setText(it.userName)
            binding.firstnameEditText.setText(it.firstName)
            binding.secondnameEditText.setText(it.secondName)
            binding.emailEditText.setText(it.email)
        }

        sharedViewModel.userUpdateResult.observe(viewLifecycleOwner) {
            when(it){
                is LoginResult.Success -> {
                    //sharedViewModel.resetValue()
                    sharedViewModel.getUserData(requireContext())
                    val action = MyProfileEditFragmentDirections.actionMyProfileEditFragmentToMyProfileFragment()
                    view.findNavController().navigate(action)
                }
                is LoginResult.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                }
                null -> { }
            }
        }

        binding.cancelButton.setOnClickListener {
            val action = MyProfileEditFragmentDirections.actionMyProfileEditFragmentToMyProfileFragment()
            view.findNavController().navigate(action)
        }

        binding.confirmButton.setOnClickListener {
            var isDataValid = true
            var isDataValidForSure = true

            if(username.text.toString().length < 2) {
                binding.usernameLayout.error = getString(R.string.invalid_username)
                isDataValidForSure = false
                isDataValid = false
            }
            if(firstName.text.toString().length < 2) {
                binding.firstnameLayout.error = getString(R.string.firstName_error)
                isDataValid = false
            }
            if(secondName.text.toString().length < 2) {
                binding.secondnameLayout.error = getString(R.string.secondName_error)
                isDataValid = false
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                binding.emailLayout.error = getString(R.string.email_error)
                isDataValid = false
            }
            val birthDate = "${
                binding.birthDate.year.toString().padStart(4, '0')
            }-${
                binding.birthDate.month.toString().padStart(2, '0')
            }-${binding.birthDate.dayOfMonth.toString().padStart(2, '0')}T00:00:00"
            if(Calendar.getInstance().get(Calendar.YEAR) - binding.birthDate.year > 118) {
                binding.birthDateLayout.error = "You can't be that old!"
                isDataValidForSure = false
            }
            var gender = ""
            when {
                binding.radioFemale.isChecked -> gender = "Female"
                binding.radioMale.isChecked -> gender = "Male"
                binding.radioOther.isChecked -> gender = "Other"
                else -> {
                    //binding.genderLayout.error = getString(R.string.gender_error)
                    isDataValid = false
                }
            }
            if(isDataValidForSure) {
                if(isDataValid) {
                    sharedViewModel.updateUserData(
                        username.text.toString(),
                        firstName.text.toString(),
                        secondName.text.toString(),
                        email.text.toString(),
                        birthDate,
                        gender,
                        requireContext()
                    )
                }
                else {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("Alert")
                        .setMessage("Not all fields have been filled in, are you sure you want to continue?")
                        .setNegativeButton("No") { _, _ ->}
                        .setPositiveButton("Yes") {dialog, which ->
                            sharedViewModel.updateUserData(
                                username.text.toString(),
                                firstName.text.toString(),
                                secondName.text.toString(),
                                email.text.toString(),
                                birthDate,
                                gender,
                                requireContext()
                            )
                        }
                        .show()
                }
            }
        }
    }
}