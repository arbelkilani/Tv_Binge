package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
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
    private val othersAdapter: ProvidersAdapter by lazy { ProvidersAdapter(this) }
    private val favoritesAdapter: ProvidersAdapter by lazy { ProvidersAdapter(this) }

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
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            launch { viewModel.selectedList.collectLatest { setFavorites(it) } }
            launch { viewModel.unSelectedList.collectLatest { setOthers(it) } }
        }
        viewModel.viewState.collectLatest { viewState ->
            when (viewState) {
                is ProvidersSelectionViewState.Error -> Log.i(
                    TAG,
                    "Exception : ${viewState.exception}"
                )
                else -> Unit
            }
        }
    }

    override fun initViews() {
        super.initViews()
        binding.rvWatchProviders.apply {
            adapter = othersAdapter
        }
        binding.rvSelectedProviders.apply {
            adapter = favoritesAdapter
        }
    }

    override fun addToFavorite(position: Int, provider: WatchProviderEntity) {
        viewModel.addToFavorite(provider)
        favoritesAdapter.apply { notifyItemInserted(0) }
        othersAdapter.apply { notifyItemRemoved(position) }
        binding.rvSelectedProviders.smoothScrollToPosition(0)
    }

    override fun showName(provider: WatchProviderEntity) {
        Toast.makeText(context, provider.name, Toast.LENGTH_SHORT).show()
    }

    override fun removeFromFavorite(position: Int, provider: WatchProviderEntity) {
        viewModel.removeFromFavorite(provider)
        othersAdapter.apply { notifyItemInserted(0) }
        favoritesAdapter.apply { notifyItemRemoved(position) }
        binding.rvWatchProviders.smoothScrollToPosition(0)
    }

    override fun setFavorites(list: List<WatchProviderEntity>) {
        favoritesAdapter.submitList(list)
    }

    override fun setOthers(list: List<WatchProviderEntity>) {
        othersAdapter.submitList(list)
    }

    override fun onDestroyView() {
        binding.rvWatchProviders.adapter = null
        binding.rvSelectedProviders.adapter = null
        super.onDestroyView()
    }

    companion object {
        private val TAG = ProvidersSelectionFragment::class.java.simpleName
    }
}