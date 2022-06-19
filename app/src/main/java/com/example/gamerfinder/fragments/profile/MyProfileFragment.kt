package com.example.gamerfinder.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gamerfinder.HomeActivity
import com.example.gamerfinder.MainActivity
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.databinding.FragmentMyProfileBinding
import com.example.gamerfinder.utils.AccountService
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: ProfileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
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
            binding.birthdateEditText.setText(it.birthDate)
            binding.genderEditText.setText(it.gender)
        }

        binding.editButton.setOnClickListener {
            val action = MyProfileFragmentDirections.actionMyProfileFragmentToMyProfileEditFragment()
            view.findNavController().navigate(action)
        }

        binding.changePasswordButton.setOnClickListener {
            val action = MyProfileFragmentDirections.actionMyProfileFragmentToChangePasswordFragment()
            view.findNavController().navigate(action)
        }

        binding.deleteButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_dialog_title))
                .setMessage(getString(R.string.delete_dialog_message))
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.delete)) { _, _ ->
                    sharedViewModel.deleteAccount(requireContext())
                }
                .show()
        }

        binding.logoutButton.setOnClickListener {
            //todo might need to remove user and token from account manager
            // but didn't figure out how yet
            //AccountService().de
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireContext().startActivity(intent)
        }

        binding.buttonToFavourites3.setOnClickListener {
            val action = MyProfileFragmentDirections.actionMyProfileFragmentToGamesListFragment()
            view.findNavController().navigate(action)
        }
    }
}