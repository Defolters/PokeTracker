package io.defolter.poketracker.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.defolter.poketracker.domain.usecase.ObservePokemonListUsecase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observePokemonListUsecase: ObservePokemonListUsecase
) : ViewModel() {

    val state = MutableLiveData(MainContract.State())
    val event = MutableLiveData<MainContract.Event>()

    private val compositeDisposable = CompositeDisposable()

    init {
        observePokemonList()
    }

    private fun observePokemonList() {
        observePokemonListUsecase
            .execute()
            .cachedIn(viewModelScope)
            .doOnSubscribe { }
            .subscribe {
                Log.d(TAG, "observePokemonListUsecase $it")
                val newState = reduce(state.value, MainContract.Effect.NewData(it))
                state.postValue(newState)
            }
            .addTo(compositeDisposable)
    }

    private fun reduce(
        state: MainContract.State?,
        effect: MainContract.Effect
    ): MainContract.State {
        return when (effect) {
            is MainContract.Effect.NewData -> {
                state?.copy(data = effect.data) ?: MainContract.State()
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}
