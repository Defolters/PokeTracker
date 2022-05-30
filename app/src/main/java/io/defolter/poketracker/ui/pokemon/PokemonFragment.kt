package io.defolter.poketracker.ui.pokemon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.defolter.poketracker.databinding.PokemonFragmentBinding
import io.defolter.poketracker.utils.loadImage

@AndroidEntryPoint
class PokemonFragment : Fragment() {

    companion object {
        private const val POKEMON_NAME_KEY = "pokemon_name"
        private val TAG = PokemonFragment::class.simpleName

        fun newInstance(name: String) = PokemonFragment().apply {
            arguments = Bundle().apply {
                putString(POKEMON_NAME_KEY, name)
            }
        }
    }

    private lateinit var binding: PokemonFragmentBinding

    private val viewModel: PokemonViewModel by viewModels()
    private var pokemonName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(POKEMON_NAME_KEY)?.let {
            pokemonName = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView $pokemonName")
        binding = PokemonFragmentBinding.inflate(inflater)

        viewModel.state.observe(viewLifecycleOwner, ::render)

        viewModel.processIntent(PokemonContract.Intent.GetPokemon(pokemonName))
        return binding.root
    }

    private fun render(state: PokemonContract.State) {
        binding.loadingTop.isVisible = state.data == null
        binding.loadingBottom.isVisible = state.evolutionChain == null

        state.data?.let {
            binding.sprite.loadImage(it.sprites.front_default)
            binding.name.text = it.name
        }
        state.evolutionChain?.let {
            binding.evolutionChain.text = it
        }
    }

}