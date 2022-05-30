package io.defolter.poketracker.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.defolter.poketracker.utils.getIdFromUrl

@JsonClass(generateAdapter = true)
data class PokemonItemResponse(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "url") val url: String
) {
    fun getImageUrl(): String {
        val index = url.getIdFromUrl()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$index.png"
    }
}
