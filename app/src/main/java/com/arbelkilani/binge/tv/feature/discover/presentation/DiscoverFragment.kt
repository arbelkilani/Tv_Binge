package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.DiscoverAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TrendingAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TvShimmerAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.ProviderClicked
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment :
    BaseFragment<FragmentDiscoverBinding>(),
    DiscoverContract.ViewCapabilities,
    ProviderClicked,
    DiscoverItemListener {

    val viewModel: DiscoverViewModel by viewModels()

    private val trendingAdapter: TrendingAdapter by lazy { TrendingAdapter() }
    private val startingThisMonthAdapter: DiscoverAdapter by lazy { DiscoverAdapter(this) }
    private val basedOnProvidersAdapter: DiscoverAdapter by lazy { DiscoverAdapter(this) }
    private val providersAdapter: ProvidersAdapter by lazy { ProvidersAdapter(this) }

    @Inject
    lateinit var tvShimmerAdapter: TvShimmerAdapter

    @Inject
    lateinit var navigator: DiscoverContract.ViewNavigation

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.viewState
            .collectLatest { viewState ->
                when (viewState) {
                    DiscoverViewState.Start -> viewModel.init()
                    DiscoverViewState.Loading -> {
                        showLoading()
                    }
                    is DiscoverViewState.Error -> showError(viewState.exception)
                    is DiscoverViewState.Loaded -> {
                        showTrending(viewState.trending)
                        showStartingThisMonth(viewState.startingThisMonth)
                        showBasedOnProviders(viewState.basedOnProvider)
                        showProviders(viewState.providers)
                    }
                }
            }
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

        binding.layoutThisMonth.rvData.setPadding(0, 0, width / 3, 0)
        binding.layoutThisMonth.rvData.adapter = startingThisMonthAdapter

        binding.layoutBasedOnProvider.rvData.setPadding(0, 0, width / 3, 0)
        binding.layoutBasedOnProvider.rvData.adapter = basedOnProvidersAdapter

        binding.rvProviders.adapter = providersAdapter
    }

    override suspend fun showTrending(state: DiscoverViewState.Trending?) {
        state?.let {
            trendingAdapter.submitData(lifecycle, it.list)
        }
    }

    override suspend fun showStartingThisMonth(state: DiscoverViewState.StartingThisMonth?) {
        state?.let {
            startingThisMonthAdapter.submitData(lifecycle, it.data)
        }
    }

    override suspend fun showBasedOnProviders(state: DiscoverViewState.BasedOnProviders?) {
        state?.let {
            basedOnProvidersAdapter.submitData(lifecycle, it.data)
        }
    }

    override suspend fun showProviders(providers: List<WatchProviderEntity>?) {
        providersAdapter.submitList(providers)
    }

    private fun showLoading() {
        binding.layoutTrending.vpTrending.apply {
            adapter = trendingAdapter.apply {
                submitData(lifecycle, PagingData.from(shimmerList))
            }
        }

        binding.layoutThisMonth.rvData.apply {
            adapter = startingThisMonthAdapter.apply {
                submitData(lifecycle, PagingData.from(shimmerList))
            }
        }

        binding.layoutBasedOnProvider.rvData.apply {
            adapter = basedOnProvidersAdapter.apply {
                submitData(lifecycle, PagingData.from(shimmerList))
            }
        }
    }

    override fun showError(exception: Exception) {
        Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onProviderClicked(watchProviderEntity: WatchProviderEntity) {
        Toast.makeText(context, watchProviderEntity.name, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val shimmerList = listOf(
            TvEntity(id = -1, "", null, null, emptyList(), 0f, ""),
            TvEntity(id = -1, "", null, null, emptyList(), 0f, ""),
            TvEntity(id = -1, "", null, null, emptyList(), 0f, ""),
            TvEntity(id = -1, "", null, null, emptyList(), 0f, ""),
            TvEntity(id = -1, "", null, null, emptyList(), 0f, ""),
        )
    }

    override fun onBackdropNull() {

    }
}