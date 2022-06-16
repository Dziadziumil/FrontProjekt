package com.example.gamerfinder.fragments.games

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gamerfinder.R
import com.example.gamerfinder.databinding.GamesListItemBinding

class GameItemAdapter : ListAdapter<Game, GameItemAdapter.GameViewHolder>(DiffCallback) {

    class GameViewHolder(
        private var binding: GamesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Game: Game) {
            binding.game = Game
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameItemAdapter.GameViewHolder {
        return GameViewHolder(GamesListItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: GameItemAdapter.GameViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.gameName == newItem.gameName
        }
    }
}

//class GameItemAdapter(
//    private val context: Context,
//    private val dataset: List<Game>
//) : RecyclerView.Adapter<GameItemAdapter.GameItemViewHolder>() {
//
//    class GameItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView = view.findViewById(R.id.game_title)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameItemViewHolder {
//        val adapterLayout = LayoutInflater.from(parent.context)
//            .inflate(R.layout.games_list_item, parent, false)
//
//        return GameItemViewHolder(adapterLayout)
//    }
//
//    override fun onBindViewHolder(holder: GameItemViewHolder, position: Int) {
//        val item = dataset[position]
//        holder.textView.text = item.gameName
//    }
//
//    override fun getItemCount(): Int = dataset.size
//}