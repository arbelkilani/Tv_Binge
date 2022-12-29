package com.arbelkilani.binge.tv.feature.discover.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TrendingAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(),
    DiscoverContract.ViewCapabilities {

    val viewModel: DiscoverViewModel by viewModels()

    private val trendingAdapter: TrendingAdapter by lazy { TrendingAdapter(binding.rvTrending) }

    @Inject
    lateinit var navigator: DiscoverContract.ViewNavigation

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.viewState.collectLatest {
            when (it) {
                DiscoverViewState.Start -> viewModel.init()
                is DiscoverViewState.HttpException -> Log.e(
                    "TAG**", "HttpException : ${it.exception}"
                )
                is DiscoverViewState.IOException -> Log.e("TAG**", "IOException : ${it.exception}")
                is DiscoverViewState.UnknownException -> Log.e("TAG**", "UnknownException")
                is DiscoverViewState.TrendingLoaded -> trendingAdapter.submitList(it.data)
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
    }
}