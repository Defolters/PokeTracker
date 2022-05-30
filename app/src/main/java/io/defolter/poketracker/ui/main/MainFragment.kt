package io.defolter.poketracker.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.defolter.poketracker.R
import io.defolter.poketracker.data.remote.model.PokemonItemResponse
import io.defolter.poketracker.databinding.MainFragmentBinding
import io.defolter.poketracker.ui.pokemon.PokemonFragment
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()

        private val TAG = MainFragment::class.simpleName
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: PokemonListAdapter
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)

        initViews()

        viewModel.state.observe(this.viewLifecycleOwner, ::render)
        viewModel.event.observe(this.viewLifecycleOwner, ::processEvent)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun initViews() {
        binding.apply {
            adapter = PokemonListAdapter()
            adapter.onItemClickListener = { name -> openDetailFragment(name) }
            adapter.addLoadStateListener { showLoading(it.source.refresh is LoadState.Loading) }

            pokemonList.layoutManager = GridLayoutManager(requireContext(), 2)
            pokemonList.adapter = adapter
        }
    }

    private fun processEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.Toast -> showToast(event.text)
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun render(state: MainContract.State) {
        Log.d(TAG, "state $state")
        state.data?.let { renderData(it) } ?: renderEmptyState()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.isVisible = isLoading
    }

    private fun renderData(data: PagingData<PokemonItemResponse>) {
        binding.pokemonList.isVisible = true
        binding.emptyText.isVisible = false

        adapter.submitData(lifecycle, data)
    }

    private fun renderEmptyState() {
        binding.pokemonList.isVisible = false
        binding.emptyText.isVisible = true
    }

    private fun openDetailFragment(name: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, PokemonFragment.newInstance(name))
            .addToBackStack(null)
            .commit()
    }
}