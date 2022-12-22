package com.arbelkilani.binge.tv.feature.onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.databinding.FragmentOnBoardingBinding
import com.arbelkilani.binge.tv.feature.onboarding.OnBoardingContract
import com.arbelkilani.binge.tv.feature.onboarding.presentation.adapter.OnBoardingPagerAdapter
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.GenreSelectionFragment
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.watchprovidersselection.WatchProvidersSelectionFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFragment :
    OnBoardingContract.ViewCapabilities,
    BaseFragment<FragmentOnBoardingBinding>() {

    val viewModel: OnBoardingViewModel by viewModels()
    private val fragments = listOf(WatchProvidersSelectionFragment(), GenreSelectionFragment())

    @Inject
    lateinit var navigator: OnBoardingContract.ViewNavigation

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                WATCH_PROVIDERS_INDEX -> Unit
            }
            super.onPageSelected(position)
        }
    }

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvents()
        initView()
    }

    override fun onResume() {
        super.onResume()
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun initEvents() {

    }

    private fun initView() {
        binding.viewPager.apply {
            adapter = OnBoardingPagerAdapter(fragments, childFragmentManager, lifecycle)
            removeOverScroll()
            isUserInputEnabled = false
            registerOnPageChangeCallback(pageChangeCallback)
        }
    }

    override fun onPause() {
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onPause()
    }

    /*override fun onCreateView(
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
    }*/

    companion object {
        private const val WATCH_PROVIDERS_INDEX = 0
    }
}