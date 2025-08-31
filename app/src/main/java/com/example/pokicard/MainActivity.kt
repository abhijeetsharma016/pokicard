package com.example.pokicard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.databinding.ActivityMainBinding
import com.example.pokicard.data.PokemonResult
import com.example.pokicard.ui.adapter.PokemonAdapter
import com.example.pokicard.ui.adapter.PokemonDetailActivity
import com.example.pokicard.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var binding: ActivityMainBinding
    private var fullpokimonlist: List<PokemonResult> = emptyList()

    private var searchJob: Job? = null
    private val DEBOUNCE_DELAY = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        observeViewModel()
        setupSearchView()

        binding.searchButton.setOnClickListener {
            performSearch()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadPokemon(isRefresh = true)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        pokemonAdapter = PokemonAdapter { pokemon ->
            val intent = Intent(this, PokemonDetailActivity::class.java)
            intent.putExtra("POKEMON_NAME", pokemon.name)
            startActivity(intent)
        }
        binding.recyclerView.apply {
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!viewModel.isLoading.value!! && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        viewModel.loadPokemon()
                    }
                }
            })
        }
    }

    private fun observeViewModel() {
        viewModel.pokemonList.observe(this) { pokemonList ->
            pokemonList?.let {
                this.fullpokimonlist = it
                val currentQuery = binding.searchBr.query.toString()
                filterPokemonList(currentQuery)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchView() {
        binding.searchBr.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank()) {
                    filterPokemonList("")
                }
                return true
            }
        })
    }

    private fun filterPokemonList(query: String) {
        if (query.isBlank()) {
            pokemonAdapter.setPokemon(fullpokimonlist)
        } else {
            val filteredPokimonList = fullpokimonlist.filter { pokimon ->
                pokimon.name.contains(query, ignoreCase = true)
            }
            pokemonAdapter.setPokemon(filteredPokimonList)
        }
    }

    private fun performSearch() {
        val query = binding.searchBr.query.toString()

        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            delay(DEBOUNCE_DELAY)

            filterPokemonList(query)
            binding.searchBr.clearFocus()
        }
    }
}