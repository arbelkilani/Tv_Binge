package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.*
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
    private val genresAdapter: GenresAdapter by lazy { GenresAdapter() }

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
                    DiscoverViewState.Loading -> showLoading()
                    is DiscoverViewState.Error -> showError(viewState.exception)
                    is DiscoverViewState.Loaded -> {
                        showTrending(viewState.trending)
                        showStartingThisMonth(viewState.startingThisMonth)
                        showBasedOnProviders(viewState.basedOnProvider)
                        showProviders(viewState.providers)
                        showGenres(viewState.genres)
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
        binding.rvGenres.adapter = genresAdapter
    }

    override fun showTrending(data: PagingData<TvEntity>) {
        trendingAdapter.submitData(lifecycle, data)
    }

    override fun showStartingThisMonth(data: PagingData<TvEntity>) {
        startingThisMonthAdapter.submitData(lifecycle, data)
    }

    override fun getMoreStartingThisMonth() {

    }

    override fun showBasedOnProviders(data: PagingData<TvEntity>) {
        basedOnProvidersAdapter.submitData(lifecycle, data)
    }

    override fun showProviders(providers: List<WatchProviderEntity>?) {
        providersAdapter.submitList(providers)
    }

    override fun showGenres(genres: List<GenreEntity>?) {
        genresAdapter.submitList(genres)
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