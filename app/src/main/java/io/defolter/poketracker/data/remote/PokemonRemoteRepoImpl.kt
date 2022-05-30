package io.defolter.poketracker.data.remote

import io.defolter.poketracker.data.remote.api.PokedexApi
import io.defolter.poketracker.data.remote.model.PokemonEvolutionChainResponse
import io.defolter.poketracker.data.remote.model.PokemonListResponse
import io.defolter.poketracker.data.remote.model.PokemonResponse
import io.defolter.poketracker.data.remote.model.PokemonSpeciesResponse
import io.defolter.poketracker.domain.repo.PokemonRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PokemonRemoteRepoImpl @Inject constructor(
    private val pokedexApi: PokedexApi
) : PokemonRepo {

    override fun getPokemonList(limit: Int, offset: Int): Single<PokemonListResponse> {
        return pokedexApi
            .getPokemonList(limit, offset)
            .subscribeOn(Schedulers.io())
    }

    override fun getPokemon(name: String): Single<PokemonResponse> {
        return pokedexApi
            .getPokemon(name)
            .subscribeOn(Schedulers.io())
    }

    override fun getPokemonSpecies(id: String): Single<PokemonSpeciesResponse> {
        return pokedexApi
            .getPokemonSpecies(id)
            .subscribeOn(Schedulers.io())
    }

    override fun getEvolutionChain(id: String): Single<PokemonEvolutionChainResponse> {
        return pokedexApi
            .getEvolutionChain(id)
            .subscribeOn(Schedulers.io())
    }
}