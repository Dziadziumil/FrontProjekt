package com.example.gamerfinder.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: ProfileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("on create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        println("view created")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.userUpdateResult.observe(viewLifecycleOwner) {
            when(it){
                is LoginResult.Success -> {
                    Toast.makeText(requireContext(), "Update Successful", Toast.LENGTH_SHORT).show()
                    sharedViewModel.resetValue()
                }
                is LoginResult.Error -> {
                    sharedViewModel.resetValue()
                }
                null -> { }
            }
        }

        sharedViewModel.passwordUpdateResult.observe(viewLifecycleOwner) {
            when(it) {
                is LoginResult.Success -> {
                    Toast.makeText(requireContext(), "Password Changed", Toast.LENGTH_SHORT).show()
                    sharedViewModel.resetValue()
                }
                is LoginResult.Error -> {
                    sharedViewModel.resetValue()
                }
                null -> { }
            }
        }

        sharedViewModel.user.observe(viewLifecycleOwner) {
            binding.usernameEditText.setText(it.userName)
            binding.firstnameEditText.setText(it.firstName)
            binding.secondnameEditText.setText(it.secondName)
            binding.emailEditText.setText(it.email)
        }

        binding.editButton.setOnClickListener {
            val action = MyProfileFragmentDirections.actionMyProfileFragmentToMyProfileEditFragment()
            view.findNavController().navigate(action)
        }

        binding.changePasswordButton.setOnClickListener {
            val action = MyProfileFragmentDirections.actionMyProfileFragmentToChangePasswordFragment()
            view.findNavController().navigate(action)
        }
    }
}