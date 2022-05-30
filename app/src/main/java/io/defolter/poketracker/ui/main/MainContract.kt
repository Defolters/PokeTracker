package io.defolter.poketracker.ui.main

import androidx.paging.PagingData
import io.defolter.poketracker.data.remote.model.PokemonItemResponse

object MainContract {
    data class State(val data: PagingData<PokemonItemResponse>? = null)

    sealed interface Intent {}

    sealed interface Effect {
        data class NewData(val data: PagingData<PokemonItemResponse>) : Effect
    }

    sealed interface Event {
        data class Toast(val text: String) : Event
    }
}