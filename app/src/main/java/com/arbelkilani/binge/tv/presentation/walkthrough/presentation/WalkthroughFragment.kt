package com.arbelkilani.binge.tv.presentation.walkthrough.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arbelkilani.binge.tv.common.base.BaseFragment
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
        binding.start.setOnClickListener {
            navigator.navigateToOnBoarding(this)
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
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager)
        { _, _ -> }.attach()
    }
}
