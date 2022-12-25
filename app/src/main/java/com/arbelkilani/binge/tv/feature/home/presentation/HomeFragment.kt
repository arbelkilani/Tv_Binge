package com.arbelkilani.binge.tv.feature.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.databinding.FragmentHomeBinding
import com.arbelkilani.binge.tv.feature.home.HomeContract
import com.arbelkilani.binge.tv.feature.home.presentation.model.HomeViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    HomeContract.ViewCapabilities,
    BaseFragment<FragmentHomeBinding>() {

    val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var navigator: HomeContract.ViewNavigation

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override suspend fun initViewModelObservation() {
        viewModel.viewState.collectLatest { viewState ->
            when (viewState) {
                HomeViewState.Start -> setNoFirstRun()
                HomeViewState.Loading -> Unit
            }
        }
    }

    override fun setNoFirstRun() {
        viewModel.setNoFirstRun()
    }
}