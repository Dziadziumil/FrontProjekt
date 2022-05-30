package com.example.gamerfinder.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
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