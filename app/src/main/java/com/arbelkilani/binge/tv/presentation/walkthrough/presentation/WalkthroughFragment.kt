package com.arbelkilani.binge.tv.presentation.walkthrough.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.databinding.FragmentWalkthroughBinding
import com.arbelkilani.binge.tv.presentation.walkthrough.WalkthroughContract
import com.arbelkilani.binge.tv.presentation.walkthrough.adapter.WalkthroughAdapter
import com.arbelkilani.binge.tv.presentation.walkthrough.model.WalkThroughNavEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class WalkthroughFragment : BaseFragment<FragmentWalkthroughBinding>(), OnPageChangeListener {

    val viewModel: WalkthroughViewModel by viewModels()

    @Inject
    lateinit var navigator: WalkthroughContract.ViewNavigation

    @Inject
    lateinit var walkthroughAdapter: WalkthroughAdapter

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

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        binding.start.isVisible = position == walkthroughAdapter.count - 1
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    private fun initView() {
        binding.viewPager.apply {
            adapter = walkthroughAdapter
            addOnPageChangeListener(this@WalkthroughFragment)
            onPageSelected(0)
        }
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}
