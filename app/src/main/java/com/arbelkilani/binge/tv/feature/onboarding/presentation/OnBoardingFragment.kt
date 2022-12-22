package com.arbelkilani.binge.tv.feature.onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.databinding.FragmentOnBoardingBinding
import com.arbelkilani.binge.tv.feature.onboarding.OnBoardingContract
import com.arbelkilani.binge.tv.feature.onboarding.presentation.adapter.OnBoardingPagerAdapter
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.GenreSelectionFragment
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.ProvidersSelectionFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding>() {

    val viewModel: OnBoardingViewModel by viewModels()
    private val fragments =
        listOf(ProvidersSelectionFragment(), GenreSelectionFragment())

    @Inject
    lateinit var navigator: OnBoardingContract.ViewNavigation

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                WATCH_PROVIDERS_INDEX -> {
                    binding.ibPrevious.isVisible = false
                    binding.ibNext.isVisible = true
                }
                else -> {
                    binding.ibPrevious.isVisible = true
                    binding.ibNext.isVisible = true
                }
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

    private fun initView() {
        binding.viewPager.apply {
            adapter = OnBoardingPagerAdapter(fragments, childFragmentManager, lifecycle)
            removeOverScroll()
            isUserInputEnabled = false
            registerOnPageChangeCallback(pageChangeCallback)
        }
    }

    override fun initEvents() {
        binding.ibNext.setOnClickListener { scrollToNext() }
        binding.ibPrevious.setOnClickListener { scrollToPrevious() }
    }

    override fun onPause() {
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onPause()
    }

    companion object {
        private const val WATCH_PROVIDERS_INDEX = 0
    }

    private fun scrollToNext() {
        val currentItem = binding.viewPager.currentItem
        if (currentItem < fragments.size - 1)
            binding.viewPager.setCurrentItem(currentItem + 1, true)
    }

    private fun scrollToPrevious() {
        val currentItem = binding.viewPager.currentItem
        if (currentItem > 0)
            binding.viewPager.setCurrentItem(currentItem - 1, true)
    }
}