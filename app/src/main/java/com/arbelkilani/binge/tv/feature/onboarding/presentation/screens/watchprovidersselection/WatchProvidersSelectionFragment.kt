package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.watchprovidersselection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.databinding.FragmentWatchProvidersSelectionBinding
import com.arbelkilani.binge.tv.feature.onboarding.OnBoardingContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchProvidersSelectionFragment :
    OnBoardingContract.ViewCapabilities,
    BaseFragment<FragmentWatchProvidersSelectionBinding>() {

    val viewModel: WatchProvidersSelectionViewModel by viewModels()

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWatchProvidersSelectionBinding {
        return FragmentWatchProvidersSelectionBinding.inflate(inflater, container, false)
    }

    override fun initViewModelObservation() {
        super.initViewModelObservation()
    }
}