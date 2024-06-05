package com.thegamers.pokemonapi.adapter

import PokemonViewModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.thegamers.pokemonapi.R
import com.thegamers.pokemonapi.model.Pokemon

class PokemonAdapter(private val onClick: (Pokemon) -> Unit) :
    ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    class PokemonViewHolder(itemView: View, val onClick: (Pokemon) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nameView: TextView = itemView.findViewById(R.id.pokemon_name)
        private val imageView: ImageView = itemView.findViewById(R.id.pokemon_image)
        private var currentPokemon: Pokemon? = null

        init {
            itemView.setOnClickListener {
                currentPokemon?.let {
                    onClick(it)
                }
            }
        }

        fun bind(pokemon: Pokemon) {
            currentPokemon = pokemon
            nameView.text = pokemon.name
            Picasso.get().load(pokemon.url).into(imageView)
        }
    }
}

class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}
