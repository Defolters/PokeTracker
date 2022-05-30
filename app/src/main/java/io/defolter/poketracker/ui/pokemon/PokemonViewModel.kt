package io.defolter.poketracker.ui.pokemon

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.defolter.poketracker.data.remote.model.PokemonResponse
import io.defolter.poketracker.domain.usecase.GetEvolutionChainUsecase
import io.defolter.poketracker.domain.usecase.GetPokemonUsecase
import io.defolter.poketracker.utils.getIdFromUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonUsecase: GetPokemonUsecase,
    private val getEvolutionChainUsecase: GetEvolutionChainUsecase
) : ViewModel() {

    val state = MutableLiveData(PokemonContract.State())
    private val compositeDisposable = CompositeDisposable()

    fun processIntent(intent: PokemonContract.Intent) {
        when (intent) {
            is PokemonContract.Intent.GetPokemon -> {
                getPokemon(intent.name)
            }
        }
    }

    private fun getPokemon(name: String) {
        getPokemonUsecase
            .execute(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::processPokemonResponse) {
                Log.d(TAG, it.toString())
            }.addTo(compositeDisposable)
    }

    private fun processPokemonResponse(pokemonResponse: PokemonResponse) {
        val newState = reduce(
            state.value,
            PokemonContract.Effect.NewPokemonData(pokemonResponse)
        )

        state.postValue(newState)
        getEvolutionChain(pokemonResponse.species.url.getIdFromUrl())
    }

    private fun getEvolutionChain(speciesId: String) {
        getEvolutionChainUsecase.execute(speciesId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::processEvolutionChainResponse) {
                Log.d(TAG, it.toString())
            }
            .addTo(compositeDisposable)
    }

    private fun processEvolutionChainResponse(evolutionChain: String) {
        val newState = reduce(
            state.value,
            PokemonContract.Effect.NewEvolutionChainData(evolutionChain)
        )

        state.postValue(newState)
    }

    @Synchronized
    private fun reduce(
        state: PokemonContract.State?,
        effect: PokemonContract.Effect
    ): PokemonContract.State {
        val oldState: PokemonContract.State = state ?: PokemonContract.State()

        return when (effect) {
            is PokemonContract.Effect.NewPokemonData -> {
                oldState.copy(data = effect.data)
            }
            is PokemonContract.Effect.NewEvolutionChainData -> {
                oldState.copy(evolutionChain = effect.evolutionChain)
            }
        }
    }

    companion object {
        private val TAG = PokemonViewModel::class.simpleName
    }
}
