package com.example.gamerfinder.fragments.lobbies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.gamerfinder.R
import com.example.gamerfinder.databinding.FragmentLobbyBinding

private const val LOBBY_ID = "lobbyId"
private const val IS_IN_LOBBY = "isInLobby"

class LobbyFragment : Fragment() {

    private var lobbyId: Int? = null
    private var isInLobby: Boolean? = null

    private var _binding: FragmentLobbyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LobbiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lobbyId = it.getInt(LOBBY_ID)
            isInLobby = it.getBoolean(IS_IN_LOBBY)
        }
        if (isInLobby == true) {
            lobbyId?.let { viewModel.getLobby(it, requireContext()) }
        } else {
            lobbyId?.let { viewModel.joinLobby(it, requireContext()) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLobbyBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.usernamesRecyclerView.adapter = UsernameItemAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}