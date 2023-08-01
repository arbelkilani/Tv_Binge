package com.arbelkilani.binge.tv.feature.details.presentation

import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.presentation.CommonListener
import com.arbelkilani.binge.tv.common.presentation.adapter.PersonAdapter
import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.databinding.FragmentTvDetailsBinding
import com.arbelkilani.binge.tv.feature.details.TvDetailsContract
import com.arbelkilani.binge.tv.feature.details.presentation.adapter.GenresAdapter
import com.arbelkilani.binge.tv.feature.details.presentation.adapter.KeywordsAdapter
import com.arbelkilani.binge.tv.feature.details.presentation.adapter.NetworksAdapter
import com.arbelkilani.binge.tv.feature.details.presentation.entities.ContentRating
import com.arbelkilani.binge.tv.feature.details.presentation.entities.ExternalId
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv
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
    TvDetailsContract.ViewCapabilities,
    CommonListener {

    private val viewModel: TvDetailsViewModel by viewModels()
    private val args by navArgs<TvDetailsFragmentArgs>()
    private val tv: Tv by lazy(LazyThreadSafetyMode.NONE) { args.tv }
    private val networksAdapter: NetworksAdapter by lazy { NetworksAdapter() }
    private val genresAdapter: GenresAdapter by lazy { GenresAdapter() }
    private val keywordsAdapter: KeywordsAdapter by lazy { KeywordsAdapter() }

    private val personAdapter: PersonAdapter by lazy {
        PersonAdapter(this)
            .apply {
                submitData(
                    viewLifecycleOwner.lifecycle,
                    PagingData.from(shimmerPerson)
                )
            }
    }

    private lateinit var behavior: BottomSheetBehavior<NestedScrollView>

    @Inject
    lateinit var navigator: TvDetailsContract.ViewNavigation

    /**
     *  Overridden methods
     */
    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTvDetailsBinding {
        return FragmentTvDetailsBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        super.initViews()
        val width = resources.displayMetrics.widthPixels
        onBackPressed()

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

        binding.rvKeywords.apply {
            (layoutManager as FlexboxLayoutManager).apply {
                flexWrap = FlexWrap.WRAP
                alignItems = AlignItems.FLEX_START
            }
            adapter = keywordsAdapter
        }

        binding.rvPersons.apply {
            setPadding((width * .038f).toInt(), 0, (width * .74f).toInt(), 0)
            adapter = personAdapter
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
                    collectCasts()
                    collectExternalId()
                    collectContentRating()
                }
                else -> Unit
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override suspend fun details(data: TvDetails) {
        binding.tvDetails = data

        binding.tvVote.isVisible = data.vote.isNotEmpty()
        binding.tvStoryLabel.isVisible = data.story.isNotEmpty()
        networksAdapter.submitList(data.networks)
        genresAdapter.submitList(tv.genres)

        // Production companies
        binding.tvCompaniesLabel.isVisible = data.productionCompanies.isNotEmpty()
        binding.tvCompanies.text = data.productionCompanies

        // First air date
        binding.tvFirstAirDateLabel.isVisible = data.firstAirDate != null
        binding.tvFirstAirDate.text = data.firstAirDate

        // Created by
        binding.tvCreatedByLabel.isVisible = data.createdBy.isNotEmpty()
        binding.tvCreatedBy.text = data.createdBy

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
        keywordsAdapter.submitList(data)
    }

    override suspend fun casts(data: List<Person>) {
        personAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.from(data))
    }

    override suspend fun showExternalId(data: ExternalId) {
        Log.i("TAG**", "showExternalId id : $data")
    }

    override suspend fun showContentRating(data: ContentRating) {
        Log.i("TAG**", "showContentRating id : $data")
    }

    override fun onPersonClicked(person: Person?) {
        person?.let {
            Toast.makeText(context, person.name, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     *  Private methods
     */
    private fun initDetailsView() {
        val height = resources.displayMetrics.heightPixels
        behavior = BottomSheetBehavior.from(binding.bottomSheetBehaviour)

        ValueAnimator.ofFloat(0f, .68f).apply {
            duration = 250
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                behavior.peekHeight = (height * animation.animatedValue as Float).toInt()
            }
            start()
        }

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 300
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                binding.ivBackdrop.alpha = value
            }
            start()
        }
        behavior.addBottomSheetCallback(bottomSheetCallback())

        binding.ivBack.setOnClickListener {
            navigator.onBackPressed(this)
        }
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

    private fun collectCasts() {
        viewModel.casts
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .onEach { state -> casts(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectExternalId() {
        viewModel.externalId
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .onEach { state -> state?.let { showExternalId(it) } }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectContentRating() {
        viewModel.contentRating
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .onEach { state -> state?.let { showContentRating(it) } }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when (behavior.state) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            binding.bottomSheetBehaviour.smoothScrollTo(0, 0)
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                        else -> navigator.onBackPressed(this@TvDetailsFragment)
                    }
                }
            })
    }

    companion object {
        private val shimmerPerson = listOf(
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
            Person(id = -1, "", ""),
        )
    }
}