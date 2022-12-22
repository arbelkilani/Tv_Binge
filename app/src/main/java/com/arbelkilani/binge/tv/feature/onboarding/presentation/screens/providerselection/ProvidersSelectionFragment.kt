package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.databinding.FragmentWatchProvidersSelectionBinding
import com.arbelkilani.binge.tv.feature.onboarding.OnBoardingContract
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.listener.ProviderSelectionListener
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.model.ProvidersSelectionViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProvidersSelectionFragment :
    OnBoardingContract.WatchProviderSelectionViewCapabilities,
    BaseFragment<FragmentWatchProvidersSelectionBinding>(),
    ProviderSelectionListener {

    val viewModel: ProvidersSelectionViewModel by viewModels()
    private val providersAdapter: ProvidersAdapter by lazy { ProvidersAdapter(this) }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWatchProvidersSelectionBinding {
        return FragmentWatchProvidersSelectionBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()
    }

    override suspend fun initViewModelObservation() {
        viewModel.viewState.collectLatest { viewState ->
            when (viewState) {
                is ProvidersSelectionViewState.Loaded -> populateWatchProviders(viewState.list)
                else -> {
                    Unit
                }
            }
        }
    }

    override fun initViews() {
        super.initViews()
        binding.rvWatchProviders.apply {
            adapter = providersAdapter
            (layoutManager as GridLayoutManager).apply {
                spanCount = 5
            }
        }
    }

    override fun onWatchProviderClicked(
        provider: WatchProviderEntity
    ) {
        viewModel.updateProvider(provider)
    }

    override fun populateWatchProviders(list: List<WatchProviderEntity>) {
        providersAdapter.submitList(list)
    }

    override fun onDestroyView() {
        binding.rvWatchProviders.adapter = null
        super.onDestroyView()
    }
}