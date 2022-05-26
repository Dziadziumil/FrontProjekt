package com.example.gamerfinder.fragments.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.gamerfinder.databinding.FragmentRegisterDecisionBinding

class RegisterDecisionFragment : Fragment() {

    private var _binding: FragmentRegisterDecisionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterDecisionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNo.setOnClickListener {
            val actionNo = RegisterDecisionFragmentDirections.actionRegisterDecisionFragmentToLoginFragment()
            view.findNavController().navigate(actionNo)
        }
        binding.buttonYes.setOnClickListener {
            val actionYes = RegisterDecisionFragmentDirections.actionRegisterDecisionFragmentToRegisterContinueFragment()
            view.findNavController().navigate(actionYes)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}