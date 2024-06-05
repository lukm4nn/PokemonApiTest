package com.thegamers.pokemonapi.ui

import PokemonViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thegamers.pokemonapi.R
import com.thegamers.pokemonapi.adapter.MyPokemonAdapter
import com.thegamers.pokemonapi.network.dao.CaughtPokemon

class MyPokemonList : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel
    private lateinit var adapter: MyPokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pokemon_list)

        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        adapter = MyPokemonAdapter(
            { pokemon -> viewModel.delete(pokemon) },
            { pokemon -> renamePokemon(pokemon) }
        )

        val recyclerView: RecyclerView = findViewById(R.id.myPokemonRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.allCaughtPokemon.observe(this, Observer { pokemons ->
            pokemons?.let { adapter.submitList(it) }
        })
    }

    private fun renamePokemon(pokemon: CaughtPokemon) {
        val newNickname = pokemon.nickname + "-" + getNextRenameIndex(pokemon.renameCount)
        val updatedPokemon = pokemon.copy(nickname = newNickname, renameCount = pokemon.renameCount + 1)
        viewModel.insert(updatedPokemon)
    }

    private fun getNextRenameIndex(renameCount: Int): Int {
        return when (renameCount) {
            0 -> 0
            1 -> 1
            else -> fibonacci(renameCount)
        }
    }

    private fun fibonacci(n: Int): Int {
        if (n <= 1) return n
        var fibo = 1
        var prevFibo = 1

        for (i in 2 until n) {
            val temp = fibo
            fibo += prevFibo
            prevFibo = temp
        }

        return fibo
    }
}