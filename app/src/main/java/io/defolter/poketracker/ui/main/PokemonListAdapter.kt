package io.defolter.poketracker.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.defolter.poketracker.R
import io.defolter.poketracker.data.remote.model.PokemonItemResponse
import io.defolter.poketracker.utils.loadImage

class PokemonListAdapter :
    PagingDataAdapter<PokemonItemResponse, PokemonListAdapter.PokemonItemViewHolder>(itemCallback) {

    var onItemClickListener: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        return PokemonItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pokemonItemResponse: PokemonItemResponse?) {
            pokemonItemResponse?.let { pokemon ->
                itemView.findViewById<ImageView>(R.id.image).loadImage(pokemon.getImageUrl())
                itemView.findViewById<TextView>(R.id.name).text = pokemon.name
                itemView.setOnClickListener { onItemClickListener(pokemon.name) }
            }
        }
    }

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<PokemonItemResponse>() {
            override fun areItemsTheSame(
                oldItem: PokemonItemResponse,
                newItem: PokemonItemResponse
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PokemonItemResponse,
                newItem: PokemonItemResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}