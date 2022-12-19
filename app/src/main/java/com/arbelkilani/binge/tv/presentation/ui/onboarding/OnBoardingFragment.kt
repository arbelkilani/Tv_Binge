package com.arbelkilani.binge.tv.presentation.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arbelkilani.binge.tv.databinding.FragmentOnBoardingBinding
import com.arbelkilani.binge.tv.presentation.ui.onboarding.adapter.ProvidersAdapter
import com.arbelkilani.binge.tv.presentation.viewmodel.onboarding.OnBoardingViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    val viewModel: OnBoardingViewModel by viewModels()

    private val providersAdapter by lazy { ProvidersAdapter() }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.rvNetworks.apply {
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.SPACE_BETWEEN
            }
            adapter = providersAdapter
        }

        lifecycleScope.launchWhenResumed {
            viewModel.provider.collectLatest {
                it.map {
                    Log.i("TAG**", "provider : $it")
                }
                providersAdapter.submitList(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}