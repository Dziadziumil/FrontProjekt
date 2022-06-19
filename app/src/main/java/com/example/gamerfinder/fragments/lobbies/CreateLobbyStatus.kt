package com.example.gamerfinder.fragments.lobbies

data class CreateLobbyStatus(
    val maxUserError: Int? = null,
    val titleError: Int? = null,
    val isDataValid: Boolean = false
)