package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.DiscoverAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TrendingAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import com.arbelkilani.binge.tv.feature.home.HomeContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment :
    BaseFragment<FragmentDiscoverBinding>(),
    DiscoverContract.ViewCapabilities,
    DiscoverItemListener {

    @Inject
    lateinit var homeNavigator: HomeContract.ViewNavigation

    private val viewModel: DiscoverViewModel by viewModels()
    private val trendingAdapter: TrendingAdapter by lazy {
        TrendingAdapter(this).apply {
            submitData(lifecycle, PagingData.from(shimmerList))
        }
    }
    private val upcomingAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this)
            .apply { submitData(lifecycle, PagingData.from(shimmerList)) }
    }

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        viewModel.viewState.onEach { viewState ->
            when (viewState) {
                is DiscoverViewState.Start -> viewModel.init()
                is DiscoverViewState.Loaded -> {
                    collectTrendingTvShows()
                    collectUpcomingTvShows()
                }
                else -> Unit
            }
        }.launchIn(scope = viewLifecycleOwner.lifecycleScope)
    }

    private fun collectTrendingTvShows() {
        viewModel.trending.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.CREATED
        ).onEach { state -> showTrending(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectUpcomingTvShows() {
        viewModel.free.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.CREATED
        ).onEach { state -> showUpcoming(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun initViews() {
        super.initViews()
        val width = resources.displayMetrics.widthPixels
        with(binding.layoutTrending) {
            vpTrending.apply {
                adapter = trendingAdapter
                removeOverScroll()
                scalePagerTransformer()
            }
        }
        binding.layoutUpcoming.rvData.apply {
            setPadding(0, 0, (width * .7f).toInt(), 0)
            adapter = upcomingAdapter
        }
    }

    override suspend fun showTrending(data: PagingData<Tv>) {
        trendingAdapter.submitData(lifecycle, data)
    }

    override suspend fun showUpcoming(data: PagingData<Tv>) {
        with(binding.layoutUpcoming) {
            tvTitle.isVisible = true
            tvTitle.text = getString(R.string.discover_upcoming)
        }
        upcomingAdapter.submitData(lifecycle, data)
    }

    override fun showError(exception: Exception) {
        Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onTvClicked(tv: Tv?) {
        tv?.let { homeNavigator.navigateToTvDetails(this, it) }
    }

    override fun onProviderClicked(watchProviderEntity: WatchProviderEntity) {
        Toast.makeText(context, watchProviderEntity.name, Toast.LENGTH_SHORT).show()
    }

    override fun onGenreClicked(genreEntity: GenreEntity) {
        TODO("Not yet implemented")
    }

    companion object {
        private val shimmerList = listOf(
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, "")
        )
    }
}