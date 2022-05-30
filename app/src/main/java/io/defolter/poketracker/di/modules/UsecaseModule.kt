package io.defolter.poketracker.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.defolter.poketracker.domain.repo.PokemonRepo
import io.defolter.poketracker.domain.usecase.GetEvolutionChainUsecase
import io.defolter.poketracker.domain.usecase.ObservePokemonListUsecase
import io.defolter.poketracker.domain.usecase.GetPokemonUsecase
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class UsecaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetPokemonListUsecase(
        @Named(RepoModule.REMOTE_REPO) pokemonRemoteRepo: PokemonRepo,
        @Named(RepoModule.LOCAL_REPO) pokemonLocalRepo: PokemonRepo
    ): ObservePokemonListUsecase {
        return ObservePokemonListUsecase(pokemonRemoteRepo)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPokemonUsecase(
        @Named(RepoModule.REMOTE_REPO) pokemonRemoteRepo: PokemonRepo,
        @Named(RepoModule.LOCAL_REPO) pokemonLocalRepo: PokemonRepo
    ): GetPokemonUsecase {
        return GetPokemonUsecase(pokemonRemoteRepo, pokemonLocalRepo)
    }

    @Provides
    @ViewModelScoped
    fun provideGetEvolutionChainUsecase(
        @Named(RepoModule.REMOTE_REPO) pokemonRemoteRepo: PokemonRepo,
        @Named(RepoModule.LOCAL_REPO) pokemonLocalRepo: PokemonRepo
    ): GetEvolutionChainUsecase {
        return GetEvolutionChainUsecase(pokemonRemoteRepo, pokemonLocalRepo)
    }
}