package io.defolter.poketracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @field:Json(name = "base_experience") val baseExperience: Int,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "is_default") val isDefault: Boolean,
    @field:Json(name = "location_area_encounters") val locationAreaEncounters: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "order") val order: Int,
    @field:Json(name = "weight") val weight: Int,
    @field:Json(name = "sprites") val sprites: PokemonSpritesResponse,
    @field:Json(name = "species") val species: SpeciesShortResponse
)
