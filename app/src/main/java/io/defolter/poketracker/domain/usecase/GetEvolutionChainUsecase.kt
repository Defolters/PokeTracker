package io.defolter.poketracker.domain.usecase

import io.defolter.poketracker.data.remote.model.EvolutionChainResponse
import io.defolter.poketracker.domain.repo.PokemonRepo
import io.defolter.poketracker.utils.getIdFromUrl
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class GetEvolutionChainUsecase @Inject constructor(
    private val pokemonRemoteRepo: PokemonRepo,
    private val pokemonLocalRepo: PokemonRepo
) {

    fun execute(speciesId: String): Single<String> {
        return pokemonRemoteRepo.getPokemonSpecies(speciesId)
            .flatMap {
                val id = it.evolutionChain?.url?.getIdFromUrl()!!
                pokemonRemoteRepo.getEvolutionChain(id)
            }
            .map { it.chain.getEvolutionChainString() }
    }

    private fun EvolutionChainResponse.getEvolutionChainString(): String {
        val chainQueue = LinkedList<EvolutionChainResponse>().also { it.add(this) }
        val stringQueue = LinkedList<String>().also { it.add(this.species.name) }

        while (chainQueue.isNotEmpty()) {
            val chain = chainQueue.poll()!!
            val string = stringQueue.poll()!!

            if (chain.evolvesTo.isNotEmpty()) {
                chain.evolvesTo.forEach {
                    chainQueue.add(it)
                    val newString = "$string => ${it.species.name}"
                    stringQueue.add(newString)
                }
            } else {
                stringQueue.add(string)
            }
        }

        return StringBuilder().apply {
            stringQueue.forEachIndexed { index, s ->
                this.append(index)
                this.append(") ")
                this.append(s)
                this.append("\n")
            }
        }.toString()
    }
}