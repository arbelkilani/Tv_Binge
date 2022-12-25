package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.databinding.FragmentWatchProvidersSelectionBinding
import com.arbelkilani.binge.tv.feature.onboarding.OnBoardingContract
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.listener.ProviderSelectionListener
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.model.ProvidersSelectionViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProvidersSelectionFragment :
    OnBoardingContract.ProviderSelectionViewCapabilities,
    BaseFragment<FragmentWatchProvidersSelectionBinding>(),
    ProviderSelectionListener {

    val viewModel: ProvidersSelectionViewModel by viewModels()
    private val otherProviders: ProvidersAdapter by lazy { ProvidersAdapter(this) }
    private val selectedProviders: ProvidersAdapter by lazy { ProvidersAdapter(this) }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWatchProvidersSelectionBinding {
        return FragmentWatchProvidersSelectionBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()
        lifecycleScope.launch {
            viewModel.selectedList.collectLatest {
                selectedProviders.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.unSelectedList.collectLatest {
                otherProviders.submitList(it)
            }
        }
    }

    override suspend fun initViewModelObservation() {
        viewModel.viewState.collectLatest { viewState ->
            //when (viewState) {
            //is ProvidersSelectionViewState.Loaded -> populate(viewState.list)
            //else -> {
            //    Unit
            //}
            //}
        }
    }

    override fun initViews() {
        super.initViews()
        binding.rvWatchProviders.apply {
            adapter = otherProviders
            (itemAnimator as? SimpleItemAnimator)?.apply {
                supportsChangeAnimations = false
                changeDuration = 0
            }
        }
        binding.rvSelectedProviders.apply {
            adapter = selectedProviders
            (itemAnimator as? SimpleItemAnimator)?.apply {
                supportsChangeAnimations = false
                changeDuration = 0
            }
        }
    }

    override fun addToFavorite(position: Int, provider: WatchProviderEntity) {
        Log.i("TAG**", "add to favorite")
        viewModel.addToFavorite(provider)
        selectedProviders.apply { notifyItemInserted(itemCount) }
        otherProviders.apply { notifyItemRemoved(position) }
    }

    override fun removeFromFavorite(position: Int, provider: WatchProviderEntity) {
        Log.i("TAG**", "remove from favorite")
        viewModel.removeFromFavorite(provider)
        otherProviders.apply { notifyItemInserted(itemCount) }
        selectedProviders.apply { notifyItemRemoved(position) }
    }

    override fun populate(list: List<WatchProviderEntity>) {
        //selectedProviders.submitList(list.filter { it.isFavorite })
        //otherProviders.submitList(list.filterNot { it.isFavorite })
    }

    override fun onDestroyView() {
        binding.rvWatchProviders.adapter = null
        binding.rvSelectedProviders.adapter = null
        super.onDestroyView()
    }
}