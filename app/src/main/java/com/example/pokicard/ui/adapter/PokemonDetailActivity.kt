package com.example.pokicard.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokemonapp.databinding.ActivityPokemonDetailBinding
import com.example.pokicard.viewmodel.PokemonDetailViewModel

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var binding: ActivityPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)

        val pokemonName = intent.getStringExtra("POKEMON_NAME")
        if (pokemonName != null) {
            viewModel.getPokemonDetail(pokemonName)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.pokemonDetail.observe(this, { pokemon ->
            pokemon?.let {
                binding.pokemonDetailName.text = "${it.name.replaceFirstChar { char -> char.uppercase() }} (#${it.id})"
                binding.pokemonDetailInfo.text = "Height: ${it.height / 10.0} m\nWeight: ${it.weight / 10.0} kg"

                val statsText = it.stats.joinToString("\n") { stat ->
                    "${stat.stat.name.replaceFirstChar { char -> char.uppercase() }}: ${stat.base_stat}"
                }
                binding.pokemonDetailStats.text = statsText

                val abilitiesText = it.abilities.joinToString("\n") { ability ->
                    ability.ability.name.replaceFirstChar { char -> char.uppercase() }
                }
                binding.pokemonDetailAbilities.text = abilitiesText

                Glide.with(this)
                    .load(it.sprites.other.officialArtwork.front_default)
                    .into(binding.pokemonDetailImage)
            }
        })
    }
}
