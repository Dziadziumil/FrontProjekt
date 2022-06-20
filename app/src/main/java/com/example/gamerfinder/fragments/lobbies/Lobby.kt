package com.example.gamerfinder.fragments.lobbies

import kotlinx.serialization.Serializable

@Serializable
data class Lobby(
    val id: Int?,
    val tittle: String?,
    val gameId: Int?,
    val ownerId: Int?,
    val description: String?,
    val maxUser: Int?,
    val currentUsers: Int?
) {
    override fun toString(): String {
        return "Lobby(id=$id, title=$tittle, gameId=$gameId, ownerId=$ownerId, description=$description, maxUser=$maxUser, currentUsers=$currentUsers)"
    }
}