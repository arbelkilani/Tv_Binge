package com.arbelkilani.binge.tv.feature.discover.presentation

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.common.ui.CarouselTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.*
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.ProviderClicked
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment :
    BaseFragment<FragmentDiscoverBinding>(),
    DiscoverContract.ViewCapabilities,
    ProviderClicked {

    val viewModel: DiscoverViewModel by viewModels()

    private val trendingAdapter: TrendingAdapter by lazy { TrendingAdapter() }
    private val airingTodayAdapter: AiringTodayAdapter by lazy { AiringTodayAdapter() }
    private val discoverAdapter: DiscoverAdapter by lazy { DiscoverAdapter() }
    private val providersAdapter: ProvidersAdapter by lazy { ProvidersAdapter(this) }

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
            .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .collect { viewState ->
                when (viewState) {
                    DiscoverViewState.Start -> viewModel.init()
                    is DiscoverViewState.Error -> showError(viewState.exception)
                    is DiscoverViewState.Loaded -> {
                        showTrending(viewState.trending)
                        showAiringToday(viewState.airingToday)
                        showDiscover(viewState.discover)
                        showProviders(viewState.providers)
                    }
                    else -> Unit
                }
            }
    }

    override fun initViews() {
        super.initViews()
        binding.rvTrending.apply {
            adapter = trendingAdapter
            removeOverScroll()
            scalePagerTransformer()
        }
        binding.rvAiringToday.apply { adapter = airingTodayAdapter }
        binding.rvDiscover.apply { adapter = discoverAdapter }
        binding.rvProviders.apply { adapter = providersAdapter }
    }

    override fun showTrending(data: List<TvEntity>) {
        //trendingAdapter.submitList(data)
        binding.viewPager.apply {
            adapter = TestAdapter(data)
            offscreenPageLimit = 3

            val width = Resources.getSystem().displayMetrics.widthPixels
            val paddingFactor = (.1F * width).toInt()
            pageMargin = (.08F * width).toInt()
            setPadding(paddingFactor, 0, paddingFactor, 0)
            setPageTransformer(false, CarouselTransformer())
        }
    }

    override fun showAiringToday(data: PagingData<TvEntity>) {
        airingTodayAdapter.submitData(lifecycle, data)
    }

    override fun showDiscover(data: PagingData<TvEntity>) {
        discoverAdapter.submitData(lifecycle, data)
    }

    override fun showProviders(data: List<WatchProviderEntity>) {
        providersAdapter.submitList(data)
    }

    override fun showError(exception: Exception) {
        Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onProviderClicked(watchProviderEntity: WatchProviderEntity) {
        Toast.makeText(context, watchProviderEntity.name, Toast.LENGTH_SHORT).show()
    }
}