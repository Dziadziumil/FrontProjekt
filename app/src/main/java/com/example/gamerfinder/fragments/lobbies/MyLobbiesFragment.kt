package com.example.gamerfinder.fragments.lobbies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.gamerfinder.databinding.FragmentMyLobbiesBinding

class MyLobbiesFragment : Fragment() {

    private var _binding: FragmentMyLobbiesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LobbiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMyLobbies(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLobbiesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.lobbiesRecyclerView.adapter = LobbyItemAdapter()

        return binding.root
    }
}