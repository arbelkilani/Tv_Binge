package com.arbelkilani.binge.tv.presentation.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arbelkilani.binge.tv.databinding.FragmentOnBoardingBinding
import com.arbelkilani.binge.tv.presentation.ui.onboarding.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.presentation.viewmodel.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    val viewModel: OnBoardingViewModel by viewModels()

    private val selectedProvidersAdapter by lazy { ProvidersAdapter() }
    private val unselectedProvidersAdapter by lazy { ProvidersAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.rvSelectedProviders.adapter = selectedProvidersAdapter
        binding.rvUnselectedProviders.adapter = unselectedProvidersAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.provider.collectLatest { list ->
                val selectedProviders = list.filter { it.isFavorite }
                val unselectedProviders = list.filter { !it.isFavorite }
                selectedProvidersAdapter.submitList(selectedProviders)
                unselectedProvidersAdapter.submitList(unselectedProviders)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}