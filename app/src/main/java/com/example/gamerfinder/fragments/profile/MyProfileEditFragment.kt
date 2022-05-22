package com.example.gamerfinder.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gamerfinder.R
import com.example.gamerfinder.databinding.FragmentMyProfileEditBinding

class MyProfileEditFragment : Fragment() {

    private var _binding: FragmentMyProfileEditBinding? = null
    private val binding get() = _binding!!

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
}