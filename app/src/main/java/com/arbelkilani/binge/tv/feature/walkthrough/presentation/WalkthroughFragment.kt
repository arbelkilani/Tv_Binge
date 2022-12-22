package com.arbelkilani.binge.tv.feature.walkthrough.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.extension.removeOverScroll
import com.arbelkilani.binge.tv.databinding.FragmentWalkthroughBinding
import com.arbelkilani.binge.tv.feature.walkthrough.WalkthroughContract
import com.arbelkilani.binge.tv.feature.walkthrough.presentation.adapter.WalkthroughPagerAdapter
import com.arbelkilani.binge.tv.feature.walkthrough.presentation.screens.FirstWalkthroughFragment
import com.arbelkilani.binge.tv.feature.walkthrough.presentation.screens.SecondWalkthroughFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WalkthroughFragment :
    WalkthroughContract.ViewCapabilities,
    BaseFragment<FragmentWalkthroughBinding>() {

    val viewModel: WalkthroughViewModel by viewModels()
    private val fragments = listOf(FirstWalkthroughFragment(), SecondWalkthroughFragment())

    @Inject
    lateinit var navigator: WalkthroughContract.ViewNavigation

    private val pageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.start.isInvisible = position != fragments.size - 1
            super.onPageSelected(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.saveResources()
    }

    override fun bindView(
        inflater: LayoutInflater, container: ViewGroup?
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

    override fun initEvents() {
        binding.start.setOnClickListener {
            navigator.navigateToOnBoarding(this)
        }
    }

    private fun initView() {
        binding.viewPager.apply {
            adapter = WalkthroughPagerAdapter(fragments, childFragmentManager, lifecycle)
            removeOverScroll()
            registerOnPageChangeCallback(pageChangeCallback)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
    }

    override fun onPause() {
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onPause()
    }
}
