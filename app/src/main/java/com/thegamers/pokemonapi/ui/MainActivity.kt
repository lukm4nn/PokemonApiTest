package com.thegamers.pokemonapi.ui

import PokemonViewModel
import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thegamers.pokemonapi.R
import com.thegamers.pokemonapi.adapter.PokemonAdapter
import com.thegamers.pokemonapi.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel
    private lateinit var adapter: PokemonAdapter
//    private lateinit var apps: Application;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnViewCacth: ImageView = findViewById(R.id.catchPokemon)

        btnViewCacth.setOnClickListener({
            val intent = Intent(this, MyPokemonList::class.java)
            startActivity(intent)
        })

        val viewModelFactory = PokemonViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PokemonViewModel::class.java)
        adapter = PokemonAdapter { pokemon ->
            val intent = Intent(this, PokemonDetail::class.java)
            intent.putExtra("POKEMON_NAME", pokemon.name)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.pokemonList.observe(this, Observer { pokemons ->
            Log.d("List Pokemon",pokemons.toString())
            pokemons?.let { adapter.submitList(it) }
        })

        viewModel.fetchPokemonList(100)

    }
}