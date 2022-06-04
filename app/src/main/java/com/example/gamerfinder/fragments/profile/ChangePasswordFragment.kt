package com.example.gamerfinder.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.databinding.FragmentChangePasswordBinding
import com.example.gamerfinder.ui.login.afterTextChanged

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: ProfileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentPassword = binding.currentPasswordEditText
        val newPassword = binding.newPasswordEditText

        newPassword.afterTextChanged {
            binding.newPasswordLayout.error = null
        }

        sharedViewModel.passwordUpdateResult.observe(viewLifecycleOwner) {
            when(it) {
                is LoginResult.Success -> {
                    sharedViewModel.resetValue()
                    val action = ChangePasswordFragmentDirections.actionChangePasswordFragmentToMyProfileFragment()
                    view.findNavController().navigate(action)
                }
                is LoginResult.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                null -> { }
            }
        }

        binding.cancelButton.setOnClickListener {
            val action = ChangePasswordFragmentDirections.actionChangePasswordFragmentToMyProfileFragment()
            view.findNavController().navigate(action)
        }

        binding.changePasswordButton.setOnClickListener {
            if(sharedViewModel.isPasswordValid(currentPassword.text.toString(), newPassword.text.toString())){
                sharedViewModel.updatePassword(currentPassword.text.toString(), newPassword.text.toString(), requireContext())
            } else {
                binding.newPasswordLayout.error = getString(R.string.invalid_password)
            }
        }


    }
}