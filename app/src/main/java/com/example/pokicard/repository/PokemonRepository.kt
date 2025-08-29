package com.example.pokicard.repository

import com.example.pokicard.data.Pokemon
import com.example.pokicard.data.PokemonListResponse
import com.example.pokicard.network.ApiService

class PokemonRepository(private val apiService: ApiService) {
    suspend fun getPokemonList(limit: Int, offset: Int) = apiService.getPokemonList(limit, offset)
    suspend fun getPokemonDetail(name: String) = apiService.getPokemonDetail(name)
}
