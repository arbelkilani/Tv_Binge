package com.arbelkilani.binge.tv.feature.home.presentation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        activity?.let {
            binding.navView.setupWithNavController(it.findNavController(R.id.nav_host_home))
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
        Log.i("TAG**", "no first run")
        viewModel.setNoFirstRun()
    }
}