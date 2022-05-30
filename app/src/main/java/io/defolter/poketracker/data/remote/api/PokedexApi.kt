package io.defolter.poketracker.data.remote.api

import io.defolter.poketracker.data.remote.model.PokemonEvolutionChainResponse
import io.defolter.poketracker.data.remote.model.PokemonListResponse
import io.defolter.poketracker.data.remote.model.PokemonResponse
import io.defolter.poketracker.data.remote.model.PokemonSpeciesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<PokemonListResponse>

    @GET("pokemon/{name}")
    fun getPokemon(
        @Path("name") name: String
    ): Single<PokemonResponse>

    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(
        @Path("id") id: String
    ): Single<PokemonSpeciesResponse>

    @GET("evolution-chain/{id}")
    fun getEvolutionChain(
        @Path("id") id: String
    ): Single<PokemonEvolutionChainResponse>
}