package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.*
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.ProviderClicked
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(), DiscoverContract.ViewCapabilities,
    ProviderClicked {

    val viewModel: DiscoverViewModel by viewModels()

    private val trendingAdapter: TrendingAdapter by lazy { TrendingAdapter() }
    private val startingThisMonthAdapter: DiscoverAdapter by lazy { DiscoverAdapter() }
    private val discoverAdapter: DiscoverAdapter by lazy { DiscoverAdapter() }

    private val airingTodayAdapter: AiringTodayAdapter by lazy { AiringTodayAdapter() }
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
        viewModel.viewState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .collect { viewState ->
                when (viewState) {
                    DiscoverViewState.Start -> viewModel.init()
                    DiscoverViewState.Loading -> {
                        showLoading()
                    }
                    is DiscoverViewState.Error -> showError(viewState.exception)
                    is DiscoverViewState.Loaded -> {
                        showTrending(viewState.trending)
                        showStartingThisMonth(viewState.startingThisMonth)
                        showDiscover(viewState.discover)

                        //showAiringToday(viewState.airingToday)
                        //showProviders(viewState.providers)
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
            vpShimmer.apply {
                adapter = tvShimmerAdapter
                scalePagerTransformer()
            }
        }

        with(binding.layoutThisMonth) {
            rvData.apply {
                setPadding(0, 0, width / 3, 0)
                adapter = startingThisMonthAdapter
            }
            rvShimmer.apply {
                setPadding(0, 0, width / 3, 0)
                adapter = tvShimmerAdapter.apply {
                    submitList(listOf())
                }
            }
        }

        with(binding.layoutDiscover) {
            rvData.apply {
                setPadding(0, 0, width / 3, 0)
                adapter = discoverAdapter
            }
            rvShimmer.apply {
                setPadding(0, 0, width / 3, 0)
                adapter = tvShimmerAdapter.apply {
                    submitList(listOf())
                }
            }
        }

        //binding.rvAiringToday.apply { adapter = airingTodayAdapter }

        //binding.rvProviders.apply { adapter = providersAdapter }
    }

    override fun showTrending(data: List<TvEntity>) {
        binding.layoutTrending.apply {
            vpTrending.visibility = View.VISIBLE
            vpShimmer.visibility = View.INVISIBLE
        }
        trendingAdapter.submitList(data)
    }

    override fun showStartingThisMonth(data: PagingData<TvEntity>) {
        binding.layoutThisMonth.apply {
            grpData.visibility = View.VISIBLE
            grpShimmer.visibility = View.INVISIBLE
            tvTitle.text = getString(R.string.discover_new_in_this_month)
        }
        binding.layoutThisMonth.rvData.adapter = startingThisMonthAdapter.apply {
            submitData(lifecycle, data)
            binding.layoutThisMonth.ivAction.isVisible = startingThisMonthAdapter.itemCount > 10
        }
    }

    override fun showDiscover(data: PagingData<TvEntity>) {
        binding.layoutDiscover.apply {
            grpData.visibility = View.VISIBLE
            grpShimmer.visibility = View.INVISIBLE
            tvTitle.text = "title is here"

        }
        binding.layoutDiscover.rvData.adapter = discoverAdapter.apply {
            submitData(lifecycle, data)
            binding.layoutDiscover.ivAction.isVisible = discoverAdapter.itemCount > 10
        }
    }

    override fun showAiringToday(data: PagingData<TvEntity>) {
        airingTodayAdapter.submitData(lifecycle, data)
    }

    override fun showProviders(data: List<WatchProviderEntity>) {
        providersAdapter.submitList(data)
    }

    private fun showLoading() {
        binding.layoutTrending.apply {
            vpTrending.visibility = View.INVISIBLE
            vpShimmer.visibility = View.VISIBLE
        }
        binding.layoutThisMonth.apply {
            grpData.visibility = View.INVISIBLE
            grpShimmer.visibility = View.VISIBLE
        }
        binding.layoutDiscover.apply {
            grpData.visibility = View.INVISIBLE
            grpShimmer.visibility = View.VISIBLE
        }
    }

    override fun showError(exception: Exception) {
        Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onProviderClicked(watchProviderEntity: WatchProviderEntity) {
        Toast.makeText(context, watchProviderEntity.name, Toast.LENGTH_SHORT).show()
    }
}