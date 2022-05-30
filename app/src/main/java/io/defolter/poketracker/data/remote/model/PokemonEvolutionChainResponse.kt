package io.defolter.poketracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonEvolutionChainResponse(
    @field:Json(name = "chain") val chain: EvolutionChainResponse
)
