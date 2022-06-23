package com.example.gamerfinder.fragments.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.gamerfinder.MainActivity
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.databinding.FragmentMyProfileBinding
import com.example.gamerfinder.ui.login.afterTextChanged
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

const val DELETE = "DELETE"

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

        sharedViewModel.deleteAccountResult.observe(viewLifecycleOwner) {
            when(it) {
                is LoginResult.Success -> {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    requireContext().startActivity(intent)
                }
                is LoginResult.Error -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Something went wrong")
                        .setMessage("Couldn't delete your account. Try again later")
                        .setNeutralButton("OK", null)
                        .show()
                }
            }
        }

        sharedViewModel.user.observe(viewLifecycleOwner) {
            binding.usernameEditText.setText(it.userName)
            binding.firstnameEditText.setText(it.firstName)
            binding.secondnameEditText.setText(it.secondName)
            binding.emailEditText.setText(it.email)
            binding.birthdateEditText.setText(it.birthDate?.dropLast(9))
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
            val alertView = layoutInflater.inflate(R.layout.delete_alert, null)
            val text = alertView.findViewById<TextInputEditText>(R.id.delete_prompt_edit_text)
            val textLayout = alertView.findViewById<TextInputLayout>(R.id.delete_prompt_layout)
            text.afterTextChanged {
                textLayout.error = null
            }
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_dialog_title))
                .setMessage(getString(R.string.delete_dialog_message))
                .setView(alertView)
                .setNegativeButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.delete), null)
                .show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (text.text.toString() != binding.usernameEditText.text.toString()) {
                    textLayout.error = "Wrong username"
                } else {
                    println("should delete")
                    sharedViewModel.deleteAccount(requireContext())
                    dialog.dismiss()
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireContext().startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}