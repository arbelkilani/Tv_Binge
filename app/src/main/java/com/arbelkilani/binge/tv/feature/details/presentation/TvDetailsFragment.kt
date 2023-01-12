package com.arbelkilani.binge.tv.feature.details.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.databinding.FragmentTvDetailsBinding
import com.arbelkilani.binge.tv.feature.details.TvDetailsContract
import com.arbelkilani.binge.tv.feature.details.presentation.model.TvDetailsViewState
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class TvDetailsFragment :
    BaseFragment<FragmentTvDetailsBinding>(),
    TvDetailsContract.ViewCapabilities {

    val viewModel: TvDetailsViewModel by viewModels()
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

    override suspend fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.viewState.map { viewState ->
            when (viewState) {
                TvDetailsViewState.Start -> viewModel.init(tv.id)
                else -> Unit
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}