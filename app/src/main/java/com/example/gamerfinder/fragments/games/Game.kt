package com.example.gamerfinder.fragments.games

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val gameName: String
) {
    override fun toString(): String {
        return "Game(id=$id, gameName='$gameName')"
    }
}