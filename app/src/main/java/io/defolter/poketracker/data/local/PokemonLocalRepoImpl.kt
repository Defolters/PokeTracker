package io.defolter.poketracker.data.local

import io.defolter.poketracker.data.remote.model.PokemonEvolutionChainResponse
import io.defolter.poketracker.data.remote.model.PokemonListResponse
import io.defolter.poketracker.data.remote.model.PokemonResponse
import io.defolter.poketracker.data.remote.model.PokemonSpeciesResponse
import io.defolter.poketracker.domain.repo.PokemonRepo
import io.reactivex.Single
import javax.inject.Inject

class PokemonLocalRepoImpl @Inject constructor(
) : PokemonRepo {

    override fun getPokemonList(limit: Int, offset: Int): Single<PokemonListResponse> {
        TODO()
    }

    override fun getPokemon(name: String): Single<PokemonResponse> {
        TODO()
    }

    override fun getPokemonSpecies(id: String): Single<PokemonSpeciesResponse> {
        TODO()
    }

    override fun getEvolutionChain(id: String): Single<PokemonEvolutionChainResponse> {
        TODO()
    }
}