package com.example.pokicard.data

data class PokemonResult(val name: String, val url: String)
data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>,
    val abilities: List<PokemonAbility>
)

data class PokemonSprites(val other: OtherSprites)
data class OtherSprites(val officialArtwork: OfficialArtwork)
data class OfficialArtwork(val front_default: String)
data class PokemonStat(val base_stat: Int, val stat: Stat)
data class Stat(val name: String)
data class PokemonAbility(val ability: Ability)
data class Ability(val name: String)
