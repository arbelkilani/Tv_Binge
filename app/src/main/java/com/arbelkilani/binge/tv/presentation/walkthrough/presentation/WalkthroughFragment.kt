package com.arbelkilani.binge.tv.presentation.walkthrough.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.databinding.FragmentWalkthroughBinding
import com.arbelkilani.binge.tv.presentation.walkthrough.WalkthroughContract
import com.arbelkilani.binge.tv.presentation.walkthrough.model.WalkThroughNavEvent
import com.arbelkilani.binge.tv.presentation.walkthrough.presentation.adapter.WalkthroughPagerAdapter
import com.arbelkilani.binge.tv.presentation.walkthrough.presentation.screens.FirstWalkthroughFragment
import com.arbelkilani.binge.tv.presentation.walkthrough.presentation.screens.SecondWalkthroughFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WalkthroughFragment : BaseFragment<FragmentWalkthroughBinding>() {

    val viewModel: WalkthroughViewModel by viewModels()
    private val fragments = listOf(FirstWalkthroughFragment(), SecondWalkthroughFragment())

    @Inject
    lateinit var navigator: WalkthroughContract.ViewNavigation

    private val pageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> binding.ibPrevious.isVisible = false
                else -> binding.ibPrevious.isVisible = true
            }
            super.onPageSelected(position)
        }
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWalkthroughBinding {
        return FragmentWalkthroughBinding.inflate(inflater, container, false)
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

    override fun initViewModelObservation() {
        viewModel.navEvent.observe(viewLifecycleOwner, this::handleNavEvent)
    }

    override fun initEvents() {
        binding.ibNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < fragments.size - 1) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            } else {
                navigator.navigateToOnBoarding(this)
            }
        }

        binding.ibPrevious.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem > 0)
                binding.viewPager.currentItem = currentItem - 1
        }
    }

    private fun handleNavEvent(navEvent: WalkThroughNavEvent) {
        when (navEvent) {
            WalkThroughNavEvent.NavigateToOnBoarding -> {
                navigator.navigate(navEvent)
            }
        }
    }

    private fun initView() {
        binding.viewPager.apply {
            adapter = WalkthroughPagerAdapter(fragments, childFragmentManager, lifecycle)
            removeOverScroll()
            isUserInputEnabled = false
            registerOnPageChangeCallback(pageChangeCallback)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager)
        { _, _ -> }.attach()
    }

    override fun onPause() {
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onPause()
    }
}
