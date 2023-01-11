package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.DiscoverAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.GenresAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TrendingAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.ProviderClicked
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment :
    BaseFragment<FragmentDiscoverBinding>(),
    DiscoverContract.ViewCapabilities,
    ProviderClicked, DiscoverItemListener {

    val viewModel: DiscoverViewModel by viewModels()

    private val trendingAdapter: TrendingAdapter by lazy {
        TrendingAdapter().apply {
            submitData(lifecycle, PagingData.from(shimmerList))
        }
    }
    private val startingThisMonthAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this).apply {
            submitData(lifecycle, PagingData.from(shimmerList))
        }
    }
    private val basedOnProvidersAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this).apply {
            submitData(lifecycle, PagingData.from(shimmerList))
        }
    }
    private val freeAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this).apply {
            submitData(lifecycle, PagingData.from(shimmerList))
        }
    }

    private val basedOnGenresAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this).apply {
            submitData(lifecycle, PagingData.from(shimmerList))
        }
    }

    private val providersAdapter: ProvidersAdapter by lazy {
        ProvidersAdapter(this).apply {
            submitList(providerShimmerTag)
        }
    }
    private val genresAdapter: GenresAdapter by lazy {
        GenresAdapter().apply {
            submitList(genreShimmerTag)
        }
    }

    @Inject
    lateinit var navigator: DiscoverContract.ViewNavigation

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        viewModel.viewState
            .map { viewState ->
                when (viewState) {
                    DiscoverViewState.Start -> viewModel.init()
                    is DiscoverViewState.Error -> showError(viewState.exception)
                    is DiscoverViewState.Data -> {
                        showTrending(viewState.trending)
                        showStartingThisMonth(viewState.startingThisMonth)
                        showBasedOnProviders(viewState.basedOnProvider)
                        showFree(viewState.free)
                        showProviders(viewState.providers)
                        showGenres(viewState.genres)
                        showBasedOnGenres(viewState.basedOnGenres)
                    }
                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
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

        binding.layoutThisMonth.rvData.apply {
            setPadding(0, 0, (width * .4f).toInt(), 0)
            adapter = startingThisMonthAdapter
        }

        binding.layoutBasedOnProvider.rvData.apply {
            setPadding(0, 0, (width * .2f).toInt(), 0)
            adapter = basedOnProvidersAdapter
        }

        binding.layoutFree.rvData.setPadding(0, 0, width / 3, 0)
        binding.layoutFree.rvData.adapter = freeAdapter

        binding.layoutProviders.rvData.apply {
            setPadding(0, 0, (width * .78f).toInt(), 0)
            adapter = providersAdapter
        }

        binding.layoutBasedOnGenre.rvData.setPadding(0, 0, width / 3, 0)
        binding.layoutBasedOnGenre.rvData.adapter = basedOnGenresAdapter

        binding.rvGenres.adapter = genresAdapter
    }

    override fun showTrending(data: PagingData<TvEntity>) {
        trendingAdapter.submitData(lifecycle, data)
    }

    override fun showStartingThisMonth(data: PagingData<TvEntity>) {
        with(binding.layoutThisMonth.tvTitle) {
            isVisible = true
            text = getString(R.string.discover_new_in_this_month)
        }
        startingThisMonthAdapter.submitData(lifecycle, data)
    }

    override fun getMoreStartingThisMonth() {

    }

    override fun showBasedOnProviders(data: PagingData<TvEntity>) {
        with(binding.layoutBasedOnProvider) {
            tvTitle.isVisible = true
            tvTitle.text = getString(R.string.discover_based_on_providers)
            val subtitle = viewModel.favoriteProviders.value
            tvSubtitle.isVisible = subtitle.isNotEmpty()
            tvSubtitle.text = subtitle
        }
        basedOnProvidersAdapter.submitData(lifecycle, data)
    }

    override fun showFree(data: PagingData<TvEntity>) {
        with(binding.layoutFree.tvTitle) {
            isVisible = true
            text = getString(R.string.discover_free_to_watch)
        }
        freeAdapter.submitData(lifecycle, data)
    }

    override fun showBasedOnGenres(data: PagingData<TvEntity>) {
        with(binding.layoutBasedOnGenre) {
            tvTitle.isVisible = true
            tvTitle.text = getString(R.string.discover_based_on_genres)
            val subtitle = viewModel.favoriteGenres.value
            tvSubtitle.isVisible = subtitle.isNotEmpty()
            tvSubtitle.text = subtitle
        }
        basedOnGenresAdapter.submitData(lifecycle, data)
    }

    override fun showProviders(providers: List<WatchProviderEntity>?) {
        providersAdapter.submitList(providers)
    }

    override fun showGenres(genres: List<GenreEntity>?) {
        genresAdapter.submitList(genres)
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
            TvEntity(id = -1, "", null, null, emptyList(), 0f, "")
        )
        private val providerShimmerTag = listOf(
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false)
        )
        private val genreShimmerTag = listOf(
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false)
        )
    }
}