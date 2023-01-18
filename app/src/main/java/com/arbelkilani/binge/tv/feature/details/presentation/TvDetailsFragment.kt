package com.arbelkilani.binge.tv.feature.details.presentation

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.databinding.FragmentTvDetailsBinding
import com.arbelkilani.binge.tv.feature.details.TvDetailsContract
import com.arbelkilani.binge.tv.feature.details.presentation.adapter.GenresAdapter
import com.arbelkilani.binge.tv.feature.details.presentation.adapter.NetworksAdapter
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class TvDetailsFragment :
    BaseFragment<FragmentTvDetailsBinding>(),
    TvDetailsContract.ViewCapabilities {

    private val viewModel: TvDetailsViewModel by viewModels()
    private val args by navArgs<TvDetailsFragmentArgs>()
    private val tv: Tv by lazy(LazyThreadSafetyMode.NONE) { args.tv }
    private val networksAdapter: NetworksAdapter by lazy { NetworksAdapter() }
    private val genresAdapter: GenresAdapter by lazy { GenresAdapter() }

    @Inject
    lateinit var navigator: TvDetailsContract.ViewNavigation

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTvDetailsBinding {
        return FragmentTvDetailsBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        super.initViews()
        binding.tv = tv
        initDetailsView()
        binding.rvNetworks.adapter = networksAdapter
        binding.rvGenres.apply {
            (layoutManager as FlexboxLayoutManager).apply {
                flexWrap = FlexWrap.WRAP
                alignItems = AlignItems.FLEX_START
            }
            adapter = genresAdapter
        }
    }

    override suspend fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.viewState.map { viewState ->
            when (viewState) {
                TvDetailsViewState.Start -> viewModel.init(tv.id)
                is TvDetailsViewState.Loaded -> {
                    collectDetails()
                    collectKeywords()
                }
                else -> Unit
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initDetailsView() {
        val height = resources.displayMetrics.heightPixels
        val behavior: BottomSheetBehavior<NestedScrollView> =
            BottomSheetBehavior.from(binding.bottomSheetBehaviour)
        behavior.peekHeight = (height * .7f).toInt()
        behavior.addBottomSheetCallback(bottomSheetCallback())
    }

    private fun collectDetails() {
        viewModel.details
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .onEach { state -> state?.let { details(state) } }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectKeywords() {
        viewModel.keywords
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .onEach { state -> keywords(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override suspend fun details(data: TvDetails) {
        binding.tvDetails = data

        binding.tvVote.isVisible = data.vote.isNotEmpty()
        binding.tvStoryLabel.isVisible = data.story.isNotEmpty()
        networksAdapter.submitList(data.networks)
        genresAdapter.submitList(tv.genres)

        // tagline
        binding.tvTagline.text = getString(R.string.tv_details_tagline, data.tagline)
        binding.tvTagline.isVisible = data.tagline.isNotEmpty()

        // next episode
        data.episodeToAir?.let {
            binding.layoutNextEpisode.isVisible = true
            binding.tvNextEpisode.text = getString(
                R.string.tv_details_next_episode,
                it.seasonNumber,
                it.episodeNumber,
                it.name
            )
            binding.tvNextEpisodeStoryLabel.isVisible = it.story.isNotEmpty()
            binding.tvNextEpisodeStory.isVisible = it.story.isNotEmpty()
        }
    }

    override suspend fun keywords(data: List<Keywords>) {
        data.map {
            Log.i("TAG**", "keyword : $it")
        }
    }

    private fun bottomSheetCallback() = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            val drawable = bottomSheet.background.mutate() as GradientDrawable
            val cornerRadius =
                resources.getDimension(R.dimen.default_corner_radius)
            drawable.cornerRadius = cornerRadius * (1.0f - slideOffset)
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.i(TvDetailsFragment::class.simpleName, "onStateChanged: $newState")
        }
    }
}