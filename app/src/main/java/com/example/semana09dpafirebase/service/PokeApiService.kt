package com.example.semana09dpafirebase.service

import com.example.semana09dpafirebase.model.PokemonModelDetails
import com.example.semana09dpafirebase.model.PokemonModelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<PokemonModelResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetails(
        @Path("name") name: String
    ): Call<PokemonModelDetails>
}