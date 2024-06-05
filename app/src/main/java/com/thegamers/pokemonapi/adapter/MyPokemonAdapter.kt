package com.thegamers.pokemonapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thegamers.pokemonapi.R
import com.thegamers.pokemonapi.network.dao.CaughtPokemon

class MyPokemonAdapter(
    private val onDeleteClick: (CaughtPokemon) -> Unit,
    private val onRenameClick: (CaughtPokemon) -> Unit
) : ListAdapter<CaughtPokemon, MyPokemonAdapter.MyPokemonViewHolder>(MyPokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.caught_pokemon_item, parent, false)
        return MyPokemonViewHolder(view, onDeleteClick, onRenameClick)
    }

    override fun onBindViewHolder(holder: MyPokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    class MyPokemonViewHolder(
        itemView: View,
        val onDeleteClick: (CaughtPokemon) -> Unit,
        val onRenameClick: (CaughtPokemon) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val nameView: TextView = itemView.findViewById(R.id.pokemon_name)
        private val imageView: ImageView = itemView.findViewById(R.id.pokemon_image)
        private val nicknameView: TextView = itemView.findViewById(R.id.pokemon_nickname)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_button)
        private val renameButton: Button = itemView.findViewById(R.id.rename_button)
        private var currentPokemon: CaughtPokemon? = null

        init {
            deleteButton.setOnClickListener {
                currentPokemon?.let {
                    onDeleteClick(it)
                }
            }
            renameButton.setOnClickListener {
                currentPokemon?.let {
                    onRenameClick(it)
                }
            }
        }

        fun bind(pokemon: CaughtPokemon) {
            currentPokemon = pokemon
            nameView.text = pokemon.name
            nicknameView.text = pokemon.nickname
            Picasso.get().load(pokemon.spriteUrl).into(imageView)
        }
    }
}

class MyPokemonDiffCallback : DiffUtil.ItemCallback<CaughtPokemon>() {
    override fun areItemsTheSame(oldItem: CaughtPokemon, newItem: CaughtPokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CaughtPokemon, newItem: CaughtPokemon): Boolean {
        return oldItem == newItem
    }
}
