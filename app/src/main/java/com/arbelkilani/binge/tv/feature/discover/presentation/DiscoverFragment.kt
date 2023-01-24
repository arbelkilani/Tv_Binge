package com.arbelkilani.binge.tv.feature.discover.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.common.presentation.CommonListener
import com.arbelkilani.binge.tv.common.presentation.adapter.PersonAdapter
import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.*
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Provider
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
    DiscoverItemListener,
    CommonListener {

    @Inject
    lateinit var navigator: HomeContract.ViewNavigation

    private val viewModel: DiscoverViewModel by viewModels()
    private val trendingAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerTv)) }
    }
    private val upcomingAdapter: UpcomingAdapter by lazy {
        UpcomingAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerTv)) }
    }
    private val talkShowsAdapter: TalkShowsAdapter by lazy {
        TalkShowsAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerTv)) }
    }
    private val documentariesAdapter: DocumentariesAdapter by lazy {
        DocumentariesAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerTv)) }
    }
    private val personAdapter: PersonAdapter by lazy {
        PersonAdapter(this)
            .apply {
                submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerPerson))
            }
    }
    private val freeAdapter: FreeShowsAdapter by lazy {
        FreeShowsAdapter(this)
            .apply {
                submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerTv))
            }
    }

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
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
                        delay(100)
                        collectTrendingTvShows()
                        collectUpcomingTvShows()
                        collectTalkShows()
                        collectDocumentaries()
                        collectPersons()
                        collectFree()
                    }
                    is DiscoverViewState.Error -> {
                        Log.i("TAG**", "exception : ${viewState.exception}")
                    }
                }
            }
    }

    private suspend fun collectTrendingTvShows() {
        viewModel.trending
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showTrendingShows(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectUpcomingTvShows() {
        viewModel.upcoming
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showUpcomingShows(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectTalkShows() {
        viewModel.talkShows
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showTalkShows(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectDocumentaries() {
        viewModel.documentaries
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showDocumentaries(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectPersons() {
        viewModel.persons
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showTrendingPersons(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectFree() {
        viewModel.free
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showFreeShows(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun initViews() {
        super.initViews()
        val width = resources.displayMetrics.widthPixels

        // Trending tv shows
        with(binding.layoutTrending) {
            vpTrending.apply {
                adapter = trendingAdapter
                removeOverScroll()
                scalePagerTransformer()
            }
        }

        // Upcoming shows
        binding.ivUpcoming.setOnClickListener { }
        binding.rvUpcoming.apply {
            setPadding(0, 0, (width * .3f).toInt(), 0)
            adapter = upcomingAdapter
        }

        // Trending persons
        binding.ivPersons.setOnClickListener { }
        binding.rvPersons.apply {
            setPadding((width * .015f).toInt(), 0, (width * .76f).toInt(), 0)
            adapter = personAdapter
        }

        // Talk shows
        binding.ivTalkShows.setOnClickListener { }
        binding.rvTalkShows.apply {
            setPadding(0, 0, (width * .7f).toInt(), 0)
            adapter = talkShowsAdapter
        }

        // Documentaries
        binding.ivDocumentaries.setOnClickListener { }
        binding.rvDocumentaries.apply {
            setPadding(0, 0, (width * .55f).toInt(), 0)
            adapter = documentariesAdapter
        }

        // Free shows
        binding.ivFree.setOnClickListener { }
        binding.rvFree.apply {
            setPadding(0, 0, (width * .3f).toInt(), 0)
            adapter = freeAdapter
        }
    }

    override suspend fun showTrendingShows(data: PagingData<Tv>) {
        trendingAdapter.submitData(lifecycle, data)
    }

    override suspend fun showUpcomingShows(data: PagingData<Tv>) {
        upcomingAdapter.submitData(lifecycle, data)
    }

    override suspend fun showTalkShows(data: PagingData<Tv>) {
        talkShowsAdapter.submitData(lifecycle, data)
    }

    override suspend fun showDocumentaries(data: PagingData<Tv>) {
        documentariesAdapter.submitData(data)
    }

    override suspend fun showTrendingPersons(data: PagingData<Person>) {
        personAdapter.submitData(lifecycle, data)
    }

    override suspend fun showFreeShows(data: PagingData<Tv>) {
        freeAdapter.submitData(lifecycle, data)
    }

    override fun showError(exception: Exception) {
        Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onTvClicked(tv: Tv?) {
        tv?.let { navigator.navigateToTvDetails(this, it) }
    }

    override fun onPersonClicked(person: Person?) {
        person?.let { Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show() }
    }

    override fun onLinkToProviderClicked(provider: Provider?) {
        provider?.let { Toast.makeText(context, provider.link, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        private val shimmerTv = listOf(
            Tv(id = -1, "", null, null, emptyList(), 0f, "", emptyList()),
            Tv(id = -1, "", null, null, emptyList(), 0f, "", emptyList()),
            Tv(id = -1, "", null, null, emptyList(), 0f, "", emptyList()),
            Tv(id = -1, "", null, null, emptyList(), 0f, "", emptyList()),
            Tv(id = -1, "", null, null, emptyList(), 0f, "", emptyList())
        )
        private val shimmerPerson = listOf(
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
        )
    }
}