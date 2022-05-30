package io.defolter.poketracker.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import androidx.paging.rxjava2.flowable
import io.defolter.poketracker.data.remote.model.PokemonItemResponse
import io.defolter.poketracker.data.remote.model.PokemonListResponse
import io.defolter.poketracker.domain.repo.PokemonRepo
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ObservePokemonListUsecase @Inject constructor(
    private val pokemonRepo: PokemonRepo,
) {

    fun execute(): Flowable<PagingData<PokemonItemResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = OFFSET,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = OFFSET * 2
            ),
            pagingSourceFactory = { PokemonListSource() }
        ).flowable
    }

    inner class PokemonListSource : RxPagingSource<Int, PokemonItemResponse>() {

        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PokemonItemResponse>> {
            val position = params.key ?: 0

            return pokemonRepo.getPokemonList(limit = OFFSET, offset = position * OFFSET)
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, position) }
                .onErrorReturn { LoadResult.Error(it) }
        }

        private fun toLoadResult(
            data: PokemonListResponse,
            position: Int
        ): LoadResult<Int, PokemonItemResponse> {
            val newPos = position * OFFSET
            return LoadResult.Page(
                data = data.results,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (newPos > data.count) null else position + 1
            )
        }

        override fun getRefreshKey(state: PagingState<Int, PokemonItemResponse>): Int? = null
    }

    companion object {
        const val OFFSET = 20
    }
}