package com.example.gamerfinder.fragments.lobbies

import kotlinx.serialization.Serializable

@Serializable
data class Lobby(
    val id: Int?,
    val title: String?,
    val gameId: Int?,
    val ownerId: Int?,
    val description: String?,
    val maxUser: Int?,
    val currentUsers: Int?
) {
    override fun toString(): String {
        return "Lobby(id=$id, title=$title, gameId=$gameId, ownerId=$ownerId, description=$description, maxUser=$maxUser, currentUsers=$currentUsers)"
    }
}