package io.defolter.poketracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EvolutionChainResponse(
    @field:Json(name = "evolves_to") val evolvesTo: List<EvolutionChainResponse>,
    @field:Json(name = "species") val species: SpeciesShortResponse
)
