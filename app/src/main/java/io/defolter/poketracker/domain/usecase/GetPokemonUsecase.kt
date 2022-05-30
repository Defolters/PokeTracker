package io.defolter.poketracker.domain.usecase

import io.defolter.poketracker.data.remote.model.PokemonResponse
import io.defolter.poketracker.domain.repo.PokemonRepo
import io.reactivex.Single
import javax.inject.Inject

class GetPokemonUsecase @Inject constructor(
    private val pokemonRemoteRepo: PokemonRepo,
    private val pokemonLocalRepo: PokemonRepo
) {
    fun execute(name: String): Single<PokemonResponse> {
        return pokemonRemoteRepo.getPokemon(name)
    }
}