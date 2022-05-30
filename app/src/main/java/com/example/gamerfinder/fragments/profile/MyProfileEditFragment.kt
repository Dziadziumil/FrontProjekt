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
import com.example.gamerfinder.databinding.FragmentMyProfileEditBinding

class MyProfileEditFragment : Fragment() {

    private var _binding: FragmentMyProfileEditBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: ProfileViewModel by activityViewModels()

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
                    sharedViewModel.resetValue()
                    sharedViewModel.getUserData(requireContext())
                    val action = MyProfileEditFragmentDirections.actionMyProfileEditFragmentToMyProfileFragment()
                    view.findNavController().navigate(action)
                }
                is LoginResult.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }

        binding.cancelButton.setOnClickListener {
            val action = MyProfileEditFragmentDirections.actionMyProfileEditFragmentToMyProfileFragment()
            view.findNavController().navigate(action)
        }

        binding.confirmButton.setOnClickListener {
            //TODO data validation

            sharedViewModel.updateUserData(
                username.text.toString(),
                firstName.text.toString(),
                secondName.text.toString(),
                email.text.toString(),
                requireContext()
            )
        }
    }
}