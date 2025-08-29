package com.example.pokicard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokicard.data.Pokemon
import com.example.pokicard.network.RetrofitClient
import com.example.pokicard.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {
    private val repository = PokemonRepository(RetrofitClient.instance)
    private val _pokemonDetail = MutableLiveData<Pokemon>()
    val pokemonDetail: LiveData<Pokemon> = _pokemonDetail

    fun getPokemonDetail(name: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonDetail(name)
                _pokemonDetail.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}