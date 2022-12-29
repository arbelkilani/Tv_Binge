package com.arbelkilani.binge.tv.feature.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.arbelkilani.binge.tv.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_discover, R.id.navigation_settings))
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_home)
        navHostFragment?.findNavController()?.let {
            binding.navView.setupWithNavController(it)
            binding.toolbar.setupWithNavController(it, appBarConfiguration)
        }
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