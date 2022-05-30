package io.defolter.poketracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "evolution_chain") val evolutionChain: EvolutionChainShortResponse?,
) {
    @JsonClass(generateAdapter = true)
    data class EvolutionChainShortResponse(
        @field:Json(name = "url") val url: String
    )
}
