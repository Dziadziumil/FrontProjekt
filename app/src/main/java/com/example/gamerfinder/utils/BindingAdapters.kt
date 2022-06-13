package com.example.gamerfinder.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gamerfinder.fragments.games.Game
import com.example.gamerfinder.fragments.games.GameItemAdapter

//@BindingAdapter("gameName")
//fun setGameName() {
//
//}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Game>?) {
    val adapter = recyclerView.adapter as GameItemAdapter
    adapter.submitList(data)
}