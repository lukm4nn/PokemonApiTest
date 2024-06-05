package com.thegamers.pokemonapi.ui

import PokemonViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.thegamers.pokemonapi.R
import com.thegamers.pokemonapi.network.dao.CaughtPokemon

class PokemonDetail : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel
    private lateinit var pokemonName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        pokemonName = intent.getStringExtra("POKEMON_NAME") ?: ""

        viewModel.pokemonDetail.observe(this, Observer { detail ->
            detail?.let { updateUI(it) }
        })

        viewModel.fetchPokemonDetail(pokemonName)

        val catchButton: Button = findViewById(R.id.catchPokemonButton)
        catchButton.setOnClickListener {
            val success = Math.random() < 0.5
            if (success) {
                val nickname = "Nickname for $pokemonName"
                val caughtPokemon = CaughtPokemon(
                    name = pokemonName,
                    nickname = nickname,
                    spriteUrl = viewModel.pokemonDetail.value?.sprites?.front_default ?: ""
                )
                viewModel.insert(caughtPokemon)
                Toast.makeText(this, "Caught $pokemonName!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to catch $pokemonName!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateUI(detail: com.thegamers.pokemonapi.model.PokemonDetail) {
        val imageView: ImageView = findViewById(R.id.pokemonImage)
        val nameView: TextView = findViewById(R.id.pokemonName)
        val typeView: TextView = findViewById(R.id.pokemonType)
        val movesView: TextView = findViewById(R.id.pokemonMoves)

        Picasso.get().load(detail.sprites.front_default).into(imageView)
        nameView.text = detail.name
        typeView.text = "Types: " + detail.types.joinToString { it.type.name }
        movesView.text = "Moves: " + detail.moves.joinToString { it.move.name }
    }
}