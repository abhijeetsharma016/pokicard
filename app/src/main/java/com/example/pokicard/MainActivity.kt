package com.example.pokicard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.databinding.ActivityMainBinding
import com.example.pokicard.ui.adapter.PokemonAdapter
import com.example.pokicard.ui.adapter.PokemonDetailActivity
import com.example.pokicard.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

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
        viewModel.pokemonList.observe(this, { pokemon ->
            pokemon?.let {
                pokemonAdapter.setPokemon(it)
            }
        })

        viewModel.isLoading.observe(this, { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}
