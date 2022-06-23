package com.example.gamerfinder.fragments.lobbies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gamerfinder.databinding.UsernameItemBinding

class UsernameItemAdapter : ListAdapter<String, UsernameItemAdapter.UsernameViewHolder>(DiffCallback) {

    class UsernameViewHolder(
        private var binding: UsernameItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(username: String) {
            binding.username = username
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsernameItemAdapter.UsernameViewHolder {
        return UsernameViewHolder(
            UsernameItemBinding.inflate(
                LayoutInflater.from(parent.context),parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UsernameItemAdapter.UsernameViewHolder, position: Int) {
        val lobby = getItem(position)
        holder.bind(lobby)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}