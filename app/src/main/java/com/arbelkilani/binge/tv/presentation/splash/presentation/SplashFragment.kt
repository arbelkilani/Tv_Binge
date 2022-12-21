package com.arbelkilani.binge.tv.presentation.splash.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.databinding.FragmentSplashBinding
import com.arbelkilani.binge.tv.presentation.splash.SplashContract
import com.arbelkilani.binge.tv.presentation.splash.model.SplashViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment :
    SplashContract.ViewCapabilities,
    BaseFragment<FragmentSplashBinding>() {


    val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var navigator: SplashContract.ViewNavigation

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun initViewModelObservation() {
        viewModel.viewState
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, this::renderState)
    }

    private fun renderState(viewState: SplashViewState) {
        when (viewState) {
            is SplashViewState.Start -> viewModel.getFirstRunState()
            is SplashViewState.IsFirstRun -> navigator.navigateToWalkthrough(this)
            is SplashViewState.IsNotFirstRun -> navigator.navigateToDashboard(this)
        }
    }
}