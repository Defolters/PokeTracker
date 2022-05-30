package io.defolter.poketracker.domain.repo

import io.defolter.poketracker.data.remote.model.PokemonEvolutionChainResponse
import io.defolter.poketracker.data.remote.model.PokemonListResponse
import io.defolter.poketracker.data.remote.model.PokemonResponse
import io.defolter.poketracker.data.remote.model.PokemonSpeciesResponse
import io.reactivex.Single

interface PokemonRepo {
    fun getPokemonList(limit: Int, offset: Int): Single<PokemonListResponse>
    fun getPokemon(name: String): Single<PokemonResponse>
    fun getPokemonSpecies(id: String): Single<PokemonSpeciesResponse>
    fun getEvolutionChain(id: String): Single<PokemonEvolutionChainResponse>
}