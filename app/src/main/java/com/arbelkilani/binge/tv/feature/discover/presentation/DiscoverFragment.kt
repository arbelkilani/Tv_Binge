package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.GenresAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.SearchListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment :
    SearchListener,
    DiscoverContract.ViewCapabilities,
    BaseFragment<FragmentDiscoverBinding>() {

    @Inject
    lateinit var navigator: DiscoverContract.ViewNavigation
    private val viewModel: DiscoverViewModel by viewModels()

    private val genresAdapter: GenresAdapter by lazy { GenresAdapter(this) }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        observeState()
    }

    private suspend fun observeState() {
        viewModel.viewState
            .collectLatest { viewState ->
                when (viewState) {
                    is DiscoverViewState.Start -> {
                        viewModel.start()
                    }
                    is DiscoverViewState.Loaded -> {
                        collectGenres()
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

    }

    private suspend fun collectGenres() {
        viewModel.genres
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showGenres(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override suspend fun showGenres(data: List<Genre>) {
        genresAdapter.submitList(data)
    }
}