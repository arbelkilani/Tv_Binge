package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.common.presentation.model.Provider
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.GenresAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TvResultAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.SearchListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : SearchListener, DiscoverContract.ViewCapabilities,
    BaseFragment<FragmentDiscoverBinding>() {

    @Inject
    lateinit var navigator: DiscoverContract.ViewNavigation
    private val viewModel: DiscoverViewModel by viewModels()

    private val genresAdapter: GenresAdapter by lazy { GenresAdapter(this) }
    private val providersAdapter: ProvidersAdapter by lazy { ProvidersAdapter(this) }
    private val showsAdapter: TvResultAdapter by lazy { TvResultAdapter() }

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        observeState()
    }

    private suspend fun observeState() {
        viewModel.viewState.collectLatest { viewState ->
            when (viewState) {
                is DiscoverViewState.Start -> {
                    viewModel.start()
                }
                is DiscoverViewState.Loaded -> {
                    delay(100)
                    collectGenres()
                    collectProviders()
                    collectShows()
                }
                else -> Unit
            }
        }
    }

    override fun initViews() {
        super.initViews()
        val width = resources.displayMetrics.widthPixels
        binding.rvGenres.apply {
            adapter = genresAdapter
        }
        binding.rvProviders.apply {
            setPadding((width * .025f).toInt(), 0, (width * .83f).toInt(), 0)
            adapter = providersAdapter
        }
        binding.rvShows.apply {
            setPadding((width * .025f).toInt(), 0, (width * .68f).toInt(), 0)
            adapter = showsAdapter
        }
        binding.etProviders.doOnTextChanged { text, _, _, _ ->
            text?.let { viewModel.filterProviders(it) }
        }
    }

    private suspend fun collectGenres() {
        viewModel.genres.flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showGenres(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectProviders() {
        viewModel.providers.flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showProviders(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectShows() {
        viewModel.shows.flowWithLifecycle(lifecycle, Lifecycle.State.CREATED).onEach { shows(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override suspend fun showGenres(data: PagingData<Genre>) {
        genresAdapter.submitData(lifecycle, data)
    }

    override suspend fun showProviders(data: PagingData<Provider>) {
        providersAdapter.submitData(lifecycle, data)
    }

    override suspend fun shows(data: PagingData<Tv>) {
        showsAdapter.submitData(lifecycle, data)
    }

    override fun onGenreSelected(genre: Genre?) {
        genre?.let { viewModel.setGenres(it) }
    }

    override fun onProviderSelected(provider: Provider?) {
        provider?.let { viewModel.setProvider(provider) }
    }
}