package com.thegamers.pokemonapi.repository

import androidx.lifecycle.LiveData
import com.thegamers.pokemonapi.network.dao.CaughtPokemon
import com.thegamers.pokemonapi.network.dao.PokemonDao

class PokemonRepository(private val pokemonDao: PokemonDao) {
    val allCaughtPokemon: LiveData<List<CaughtPokemon>> = pokemonDao.getAllCaughtPokemon()

    suspend fun insert(caughtPokemon: CaughtPokemon) {
        pokemonDao.insert(caughtPokemon)
    }

    suspend fun delete(caughtPokemon: CaughtPokemon) {
        pokemonDao.delete(caughtPokemon)
    }
}

