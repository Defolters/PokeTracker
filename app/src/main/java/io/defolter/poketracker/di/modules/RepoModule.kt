package io.defolter.poketracker.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.defolter.poketracker.data.local.PokemonLocalRepoImpl
import io.defolter.poketracker.data.remote.PokemonRemoteRepoImpl
import io.defolter.poketracker.data.remote.api.PokedexApi
import io.defolter.poketracker.domain.repo.PokemonRepo
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    @Named(REMOTE_REPO)
    fun provideRemotePokemonRepo(
        pokedexApi: PokedexApi
    ): PokemonRepo {
        return PokemonRemoteRepoImpl(pokedexApi)
    }

    @Provides
    @Singleton
    @Named(LOCAL_REPO)
    fun provideLocalPokemonRepo(
    ): PokemonRepo {
        return PokemonLocalRepoImpl()
    }

    companion object {
        const val LOCAL_REPO = "local_repo"
        const val REMOTE_REPO = "remote_repo"
    }

}