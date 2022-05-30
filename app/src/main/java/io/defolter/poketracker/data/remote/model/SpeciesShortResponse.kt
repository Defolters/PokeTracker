package io.defolter.poketracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpeciesShortResponse(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "url") val url: String
)
