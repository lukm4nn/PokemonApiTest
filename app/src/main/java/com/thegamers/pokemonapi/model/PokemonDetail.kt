package com.thegamers.pokemonapi.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>,
    val moves: List<PokemonMove>
)
