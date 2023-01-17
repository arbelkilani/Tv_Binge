package com.arbelkilani.binge.tv.feature.details.presentation

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.databinding.FragmentTvDetailsBinding
import com.arbelkilani.binge.tv.feature.details.TvDetailsContract
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class TvDetailsFragment :
    BaseFragment<FragmentTvDetailsBinding>(),
    TvDetailsContract.ViewCapabilities {

    private val viewModel: TvDetailsViewModel by viewModels()
    private val args by navArgs<TvDetailsFragmentArgs>()
    private val tv: Tv by lazy(LazyThreadSafetyMode.NONE) { args.tv }

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
    }

    override suspend fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.viewState.map { viewState ->
            when (viewState) {
                TvDetailsViewState.Start -> viewModel.init(tv.id)
                is TvDetailsViewState.Data -> {
                    views(viewState.data)
                    binding.tvDetails = viewState.data
                }
                else -> Unit
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun views(tvDetails: TvDetails) {
        binding.tvVote.isVisible = tvDetails.vote.isNotEmpty()
        binding.tvStoryLabel.isVisible = tvDetails.story.isNotEmpty()
    }

    private fun initDetailsView() {
        val height = resources.displayMetrics.heightPixels
        val behavior: BottomSheetBehavior<NestedScrollView> =
            BottomSheetBehavior.from(binding.bottomSheetBehaviour)
        behavior.peekHeight = (height * .7f).toInt()
        behavior.addBottomSheetCallback(bottomSheetCallback())
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