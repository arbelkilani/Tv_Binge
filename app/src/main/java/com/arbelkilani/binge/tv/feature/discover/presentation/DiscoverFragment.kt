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
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.TalkShowsAdapter
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import com.arbelkilani.binge.tv.feature.home.HomeContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
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
    private val trendingAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this)
            .apply { submitData(lifecycle, PagingData.from(shimmerList)) }
    }
    private val upcomingAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this)
            .apply { submitData(lifecycle, PagingData.from(shimmerList)) }
    }
    private val talkShows: TalkShowsAdapter by lazy {
        TalkShowsAdapter(this)
            .apply { submitData(lifecycle, PagingData.from(shimmerList)) }
    }

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        viewModel.viewState
            .collectLatest { viewState ->
                when (viewState) {
                    is DiscoverViewState.Start -> viewModel.init()
                    is DiscoverViewState.Loaded -> {
                        delay(100)
                        collectTrendingTvShows()
                        collectUpcomingTvShows()
                        collectTalkShows()
                    }
                    else -> Unit
                }
            }
    }

    private suspend fun collectTrendingTvShows() {
        viewModel.trending
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach {
                showTrending(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectUpcomingTvShows() {
        viewModel.upcoming
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach {
                showUpcoming(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectTalkShows() {
        viewModel.talkShows
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach {
                showTalkShows(it)
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
        with(binding.layoutUpcoming) {
            rvData.apply {
                setPadding(0, 0, (width * .4f).toInt(), 0)
                adapter = upcomingAdapter
            }
        }
        with(binding.layoutTalkShows) {
            rvData.apply {
                setPadding(0, 0, (width * .3f).toInt(), 0)
                adapter = talkShows
            }
        }
    }

    override suspend fun showTrending(data: PagingData<Tv>) {
        trendingAdapter.submitData(lifecycle, data)
    }

    override suspend fun showUpcoming(data: PagingData<Tv>) {
        with(binding.layoutUpcoming) {
            tvTitle.isVisible = true
            tvTitle.text = getString(R.string.discover_title_upcoming)
        }
        upcomingAdapter.submitData(lifecycle, data)
    }

    override suspend fun showTalkShows(data: PagingData<Tv>) {
        with(binding.layoutTalkShows) {
            tvTitle.isVisible = true
            tvTitle.text = getString(R.string.discover_title_talk_shows)
        }
        talkShows.submitData(lifecycle, data)
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