package com.example.gamerfinder.fragments.lobbies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLobbiesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.lobbiesRecyclerView.adapter = LobbyItemAdapter()
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

    companion object {
        @JvmStatic
        fun newInstance(gameId: Int) =
            LobbiesFragment().apply {
                arguments = Bundle().apply {
                    putInt(GAME_ID, gameId)
                }
            }
    }
}