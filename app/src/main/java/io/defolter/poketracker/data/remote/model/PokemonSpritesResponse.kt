package io.defolter.poketracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpritesResponse(
    @field:Json(name = "front_default") val front_default: String?,
    @field:Json(name = "front_shiny") val front_shiny: String?,
    @field:Json(name = "front_female") val front_female: String?,
    @field:Json(name = "front_shiny_female") val front_shiny_female: String?,
    @field:Json(name = "back_default") val back_default: String?,
    @field:Json(name = "back_shiny") val back_shiny: String?,
    @field:Json(name = "back_female") val back_female: String?,
    @field:Json(name = "back_shiny_female") val back_shiny_female: String?
)
