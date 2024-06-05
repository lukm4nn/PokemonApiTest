package com.thegamers.pokemonapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonApi {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: PokemonApiService = retrofit.create(PokemonApiService::class.java)

}