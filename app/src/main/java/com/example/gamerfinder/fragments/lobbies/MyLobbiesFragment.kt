package com.example.gamerfinder.fragments.lobbies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.gamerfinder.databinding.FragmentMyLobbiesBinding

class MyLobbiesFragment : Fragment() {

    private var _binding: FragmentMyLobbiesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LobbiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMyLobbies(requireContext())
        viewModel.getUsersInLobbies(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLobbiesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.lobbiesRecyclerView.adapter = LobbyItemAdapter {
            val isInLobby = viewModel.isInLobby(it.id, requireContext())
            val action = MyLobbiesFragmentDirections.actionMyLobbiesFragmentToLobbyFragment(it.id, isInLobby)
            binding.root.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}