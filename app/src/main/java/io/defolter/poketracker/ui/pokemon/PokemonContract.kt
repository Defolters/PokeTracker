package io.defolter.poketracker.ui.pokemon

import io.defolter.poketracker.data.remote.model.PokemonResponse

object PokemonContract {
    sealed interface Intent {
        data class GetPokemon(val name: String) : Intent
    }

    sealed interface Effect {
        data class NewPokemonData(val data: PokemonResponse) : Effect
        data class NewEvolutionChainData(val evolutionChain: String) : Effect
    }

    data class State(
        val data: PokemonResponse? = null,
        val evolutionChain: String? = null,
    )

    sealed interface Event {
        data class Toast(val text: String) : Event
    }
}