package com.arbelkilani.binge.tv.presentation.walkthrough.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    @Inject
    lateinit var navigator: WalkthroughContract.ViewNavigation

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

    override fun initViewModelObservation() {
        viewModel.navEvent.observe(viewLifecycleOwner, this::handleNavEvent)
    }

    override fun initEvents() {
        binding.ibAction.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < 1) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            } else {
                navigator.navigateToOnBoarding(this)
            }
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
        // TODO: create a custom item to replace circle in xml file
        binding.viewPager.apply {
            adapter = WalkthroughPagerAdapter(
                listOf(
                    FirstWalkthroughFragment(),
                    SecondWalkthroughFragment()
                ), parentFragmentManager, lifecycle
            )
            removeOverScroll()
            isUserInputEnabled = false
            object : OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    Log.i("TAG**", "onPageScrolled : $position")
                }
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.i("TAG**", "onPageSelected : $position")
                }
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager)
        { _, _ -> }.attach()
    }
}
