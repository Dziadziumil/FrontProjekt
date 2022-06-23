package com.example.gamerfinder.fragments.lobbies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gamerfinder.R
import com.example.gamerfinder.databinding.FragmentLobbiesBinding
import com.example.gamerfinder.fragments.games.GameItemAdapter

private const val GAME_ID = "gameId"
private const val GAME_NAME = "gameName"

class LobbiesFragment : Fragment() {

    private var _binding: FragmentLobbiesBinding? = null
    private val binding get() = _binding!!

    private var gameId: Int? = null
    private lateinit var gameName: String

    private val viewModel: LobbiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameId = it.getInt(GAME_ID)
            gameName = it.getString(GAME_NAME).toString()
        }
        gameId?.let { viewModel.getLobbies(it, requireContext()) }
        viewModel.getUsersInLobbies(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLobbiesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.lobbiesRecyclerView.adapter = LobbyItemAdapter {
            val isInLobby = viewModel.isInLobby(it.id, requireContext())
            val action = LobbiesFragmentDirections.actionLobbiesFragmentToLobbyFragment(it.id, isInLobby)
            binding.root.findNavController().navigate(action)
        }
        binding.fragmentTitle.text = gameName

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(gameId)

        binding.fabCreateLobby.setOnClickListener {
            val action = LobbiesFragmentDirections.actionLobbiesFragmentToCreateLobbyFragment(gameId!!, gameName)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}