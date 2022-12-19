package com.arbelkilani.binge.tv.presentation.ui.walkthrough

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.arbelkilani.binge.tv.databinding.FragmentWalkthroughBinding
import com.arbelkilani.binge.tv.presentation.ui.walkthrough.adapter.WalkthroughAdapter
import com.arbelkilani.binge.tv.presentation.viewmodel.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WalkthroughFragment : Fragment(), OnPageChangeListener {

    private var _binding: FragmentWalkthroughBinding? = null
    private val binding get() = _binding!!

    val viewModel: OnBoardingViewModel by viewModels()

    @Inject
    lateinit var walkthroughAdapter: WalkthroughAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalkthroughBinding.inflate(inflater, container, false)
        binding.viewPager.apply {
            adapter = walkthroughAdapter
            addOnPageChangeListener(this@WalkthroughFragment)
            onPageSelected(0)
        }
        binding.viewPager.adapter = walkthroughAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        binding.start.isVisible = position == walkthroughAdapter.count - 1
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}