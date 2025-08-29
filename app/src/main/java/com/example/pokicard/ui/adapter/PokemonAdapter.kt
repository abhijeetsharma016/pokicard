package com.example.pokicard.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokicard.data.PokemonResult


class PokemonAdapter(private val onPokemonClicked: (PokemonResult) -> Unit) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var pokemonList = listOf<PokemonResult>()

    fun setPokemon(pokemon: List<PokemonResult>) {
        this.pokemonList = pokemon
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener { onPokemonClicked(pokemon) }
    }

    override fun getItemCount() = pokemonList.size

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.pokemonName)
        private val imageView: ImageView = itemView.findViewById(R.id.pokemonImage)

        fun bind(pokemon: PokemonResult) {
            nameTextView.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val pokemonId = pokemon.url.split("/").dropLast(1).last()
            val imageUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"

            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // Add a placeholder drawable
                .into(imageView)
        }
    }
}
