package com.arbelkilani.binge.tv.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arbelkilani.binge.tv.databinding.FragmentOnBoardingBinding
import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.presentation.onboarding.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.presentation.onboarding.listener.WatchProviderListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class OnBoardingFragment : Fragment(), WatchProviderListener {

    private var _binding: FragmentOnBoardingBinding? = null
    val viewModel: OnBoardingViewModel by viewModels()

    private val providersAdapter by lazy { ProvidersAdapter(this) }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.rvProviders.apply {
            adapter = providersAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.provider.collectLatest { list ->
                providersAdapter.submitList(list)
            }
        }

        binding.next.setOnClickListener {
            viewModel.next()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onWatchProviderClicked(provider: WatchProviderEntity, isSelected: Boolean) {
        viewModel.onProviderClicked(provider, isSelected)
    }
}