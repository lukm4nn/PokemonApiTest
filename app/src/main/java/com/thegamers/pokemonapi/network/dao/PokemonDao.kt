package com.thegamers.pokemonapi.network.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(caughtPokemon: CaughtPokemon)

    @Delete
    fun delete(caughtPokemon: CaughtPokemon)

    @Query("SELECT * FROM caught_pokemon")
    fun getAllCaughtPokemon(): LiveData<List<CaughtPokemon>>
}