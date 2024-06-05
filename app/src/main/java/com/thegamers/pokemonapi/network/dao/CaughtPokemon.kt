package com.thegamers.pokemonapi.network.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "caught_pokemon")
data class CaughtPokemon(
    @PrimaryKey val name: String,
    val nickname: String,
    val spriteUrl: String,
    val renameCount: Int = 0
)