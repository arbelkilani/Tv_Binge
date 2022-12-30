package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.AiringTodayAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TrendingAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(),
    DiscoverContract.ViewCapabilities {

    val viewModel: DiscoverViewModel by viewModels()

    private val trendingAdapter: TrendingAdapter by lazy { TrendingAdapter() }
    private val airingTodayAdapter: AiringTodayAdapter by lazy { AiringTodayAdapter() }

    @Inject
    lateinit var navigator: DiscoverContract.ViewNavigation

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        super.initViewModelObservation()

        viewModel.viewState.collect { viewState ->
            when (viewState) {
                DiscoverViewState.Start -> viewModel.init()
                is DiscoverViewState.Data -> {
                    when (viewState) {
                        is DiscoverViewState.Data.TrendingState.Success -> showTrending(viewState.trending)
                        is DiscoverViewState.Data.AiringTodayState.Success -> showAiringToday(
                            viewState.airingToday
                        )
                        else -> Unit
                    }
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
        binding.rvAiringToday.apply {
            adapter = airingTodayAdapter
        }
    }

    override fun showTrending(data: List<TvEntity>) {
        trendingAdapter.submitList(data)
        binding.rvTrending.setCurrentItem(data.size / 2, false)
    }

    override fun showAiringToday(data: PagingData<TvEntity>) {
        airingTodayAdapter.submitData(lifecycle, data)
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}