package com.thegamers.pokemonapi.network

import com.thegamers.pokemonapi.model.PokemonDetail
import com.thegamers.pokemonapi.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetail

}