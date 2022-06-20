package com.example.gamerfinder.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gamerfinder.fragments.games.Game
import com.example.gamerfinder.fragments.games.GameItemAdapter
import com.example.gamerfinder.fragments.lobbies.Lobby
import com.example.gamerfinder.fragments.lobbies.LobbyItemAdapter

//@BindingAdapter("gameName")
//fun setGameName() {
//
//}

@BindingAdapter("gamesData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Game>?) {
    val adapter = recyclerView.adapter as GameItemAdapter
    adapter.submitList(data)
}

@BindingAdapter("lobbiesData")
fun bindRecyclerView2(recyclerView: RecyclerView, data: List<Lobby>?) {
    val adapter = recyclerView.adapter as LobbyItemAdapter
    adapter.submitList(data)
}