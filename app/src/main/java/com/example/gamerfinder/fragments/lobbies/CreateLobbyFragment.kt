package com.example.gamerfinder.fragments.lobbies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gamerfinder.R
import com.example.gamerfinder.activities.loginactivity.LoginResult
import com.example.gamerfinder.activities.loginactivity.LoginViewModel
import com.example.gamerfinder.databinding.FragmentCreateLobbyBinding
import kotlin.properties.Delegates

private const val GAME_ID = "gameId"
private const val GAME_NAME = "gameName"

class CreateLobbyFragment : Fragment() {

    private var _binding: FragmentCreateLobbyBinding? = null
    private val binding get() = _binding!!

    private var gameId: Int? = null
    private lateinit var gameName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameId = it.getInt(GAME_ID)
            gameName = it.getString(GAME_NAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: LobbyCreateViewModel by viewModels()
        binding.cancelButton.setOnClickListener {
            val action = CreateLobbyFragmentDirections.actionCreateLobbyFragmentToLobbiesFragment(gameId!!, gameName)
            findNavController().navigate(action)
        }

        binding.createButton.setOnClickListener {
            viewModel.createLobby(
                gameId!!,
                binding.lobbyTitleEditText.text.toString(),
                binding.lobbyDescriptionEditText.text.toString(),
                binding.lobbySizeEditText.text.toString(),
                requireContext()
            )
        }

        viewModel.createLobbyResult.observe(viewLifecycleOwner) {
            when (it) {
                is LobbyCreateResult.Error -> Toast.makeText(
                    requireContext(),
                    it.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                is LobbyCreateResult.Success -> {
                    val action = CreateLobbyFragmentDirections.actionCreateLobbyFragmentToLobbiesFragment(gameId!!, gameName)
                    findNavController().navigate(action)
                    Toast.makeText(
                        requireContext(),
                        it.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.createLobbyStatus.observe(viewLifecycleOwner) {
            if (it.titleError != null) {
                binding.lobbyTitleLayout.error = getString(it.titleError)
            }
            if (it.maxUserError != null) {
                binding.lobbySizeLayout.error = getString(it.maxUserError)
            }
            if (it.isDataValid) {
                binding.lobbyTitleLayout.error = null
                binding.lobbySizeLayout.error = null
            }
        }

    }
}