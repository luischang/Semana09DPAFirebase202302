package com.example.semana09dpafirebase.model

data class PokemonModelDetails(
    val name: String,
    val sprites: Sprites,
    val abilities: List<AbilityModel>
)
