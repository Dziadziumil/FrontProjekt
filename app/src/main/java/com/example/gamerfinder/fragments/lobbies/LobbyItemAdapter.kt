package com.example.gamerfinder.fragments.lobbies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gamerfinder.databinding.LobbyListItemBinding
import com.example.gamerfinder.utils.AccountService

class LobbyItemAdapter : ListAdapter<Lobby, LobbyItemAdapter.LobbyViewHolder>(DiffCallback) {

    class LobbyViewHolder(
        private var binding: LobbyListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lobby: Lobby) {
            binding.lobby = lobby
            if(AccountService(binding.root.context).getCurrentUserId().toInt() == lobby.ownerId) {
                binding.joinButton.isEnabled = false
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LobbyViewHolder {
        return LobbyViewHolder(
            LobbyListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) {
        val lobby = getItem(position)
        holder.bind(lobby)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Lobby>() {
        override fun areItemsTheSame(oldItem: Lobby, newItem: Lobby): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lobby, newItem: Lobby): Boolean {
            return oldItem.title == newItem.title && oldItem.ownerId == newItem.ownerId
        }

    }
}