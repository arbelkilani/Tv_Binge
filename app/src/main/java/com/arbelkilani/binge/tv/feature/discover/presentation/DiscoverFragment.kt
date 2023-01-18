package com.arbelkilani.binge.tv.feature.discover.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.common.extension.scalePagerTransformer
import com.arbelkilani.binge.tv.databinding.FragmentDiscoverBinding
import com.arbelkilani.binge.tv.feature.discover.DiscoverContract
import com.arbelkilani.binge.tv.feature.discover.presentation.adapter.*
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.DiscoverViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Person
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
    lateinit var navigator: HomeContract.ViewNavigation

    private val viewModel: DiscoverViewModel by viewModels()
    private val trendingAdapter: DiscoverAdapter by lazy {
        DiscoverAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerList)) }
    }
    private val upcomingAdapter: UpcomingAdapter by lazy {
        UpcomingAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerList)) }
    }
    private val talkShowsAdapter: TalkShowsAdapter by lazy {
        TalkShowsAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerList)) }
    }
    private val providersAdapter: ProvidersAdapter by lazy {
        ProvidersAdapter(this)
            .apply { submitList(shimmerListProviders) }
    }
    private val genresAdapter: GenresAdapter by lazy {
        GenresAdapter(this)
            .apply { submitList(shimmerListGenres) }
    }
    private val documentariesAdapter: DocumentariesAdapter by lazy {
        DocumentariesAdapter(this)
            .apply { submitData(viewLifecycleOwner.lifecycle, PagingData.from(shimmerList)) }
    }
    private val personAdapter: PersonAdapter by lazy { PersonAdapter() }

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
                        collectProviders()
                        collectGenres()
                        collectDocumentaries()
                        collectPersons()
                    }
                    else -> Unit
                }
            }
    }

    private suspend fun collectTrendingTvShows() {
        viewModel.trending
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showTrending(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectUpcomingTvShows() {
        viewModel.upcoming
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showUpcoming(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectTalkShows() {
        viewModel.talkShows
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showTalkShows(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectProviders() {
        viewModel.providers
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showProviders(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun collectGenres() {
        viewModel.genres
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { showGenres(it) }
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
        binding.rvUpcoming.apply {
            setPadding(0, 0, (width * .45f).toInt(), 0)
            adapter = upcomingAdapter
        }
        binding.rvTalkShows.apply {
            setPadding(0, 0, (width * .72f).toInt(), 0)
            adapter = talkShowsAdapter
        }
        binding.rvProviders.apply {
            setPadding(0, 0, (width * .82f).toInt(), 0)
            adapter = providersAdapter
        }
        binding.rvGenres.adapter = genresAdapter
        binding.rvDocumentaries.apply {
            setPadding(0, 0, (width * .45f).toInt(), 0)
            adapter = documentariesAdapter
        }
        binding.rvPersons.apply {
            setPadding(20, 0, (width * .78f).toInt(), 0)
            adapter = personAdapter
        }
    }

    override suspend fun showTrending(data: PagingData<Tv>) {
        trendingAdapter.submitData(lifecycle, data)
    }

    override suspend fun showUpcoming(data: PagingData<Tv>) {
        upcomingAdapter.submitData(lifecycle, data)
    }

    override suspend fun showTalkShows(data: PagingData<Tv>) {
        talkShowsAdapter.submitData(lifecycle, data)
    }

    override suspend fun showProviders(data: List<WatchProviderEntity>) {
        providersAdapter.submitList(data)
    }

    override suspend fun showGenres(data: List<GenreEntity>) {
        genresAdapter.submitList(data)
    }

    override suspend fun showDocumentaries(data: PagingData<Tv>) {
        documentariesAdapter.submitData(data)
    }

    override suspend fun showTrendingPersons(data: PagingData<Person>) {
        personAdapter.submitData(lifecycle, data)
    }

    override fun showError(exception: Exception) {
        Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onTvClicked(tv: Tv?) {
        tv?.let { navigator.navigateToTvDetails(this, it) }
    }

    override fun onProviderClicked(watchProviderEntity: WatchProviderEntity) {
        navigator.navigateToShowsFromProvider(this, watchProviderEntity)
    }

    override fun onGenreClicked(genreEntity: GenreEntity) {
        navigator.navigateToShowsFromGenre(this, genreEntity)
    }

    companion object {
        private val shimmerList = listOf(
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, ""),
            Tv(id = -1, "", null, null, emptyList(), 0f, "")
        )

        private val shimmerListProviders = listOf(
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
            WatchProviderEntity(id = -1, "", "", 0, false),
        )

        private val shimmerListGenres = listOf(
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
            GenreEntity(id = -1, "", false),
        )
    }
}