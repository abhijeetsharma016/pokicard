package com.example.pokicard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokicard.data.PokemonResult
import com.example.pokicard.network.RetrofitClient
import com.example.pokicard.repository.PokemonRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = PokemonRepository(RetrofitClient.instance)
    private val _pokemonList = MutableLiveData<List<PokemonResult>>()
    val pokemonList: LiveData<List<PokemonResult>> = _pokemonList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 0
    private val pageSize = 20

    init {
        loadPokemon()
    }

    fun loadPokemon(isRefresh: Boolean = false) {
        if (isRefresh) currentPage = 0
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val offset = currentPage * pageSize
                val response = repository.getPokemonList(pageSize, offset)
                if (isRefresh) {
                    _pokemonList.value = response.results
                } else {
                    val currentList = _pokemonList.value ?: emptyList()
                    _pokemonList.value = currentList + response.results
                }
                currentPage++
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}