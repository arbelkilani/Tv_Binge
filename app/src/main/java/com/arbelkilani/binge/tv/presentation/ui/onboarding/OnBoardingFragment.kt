package com.arbelkilani.binge.tv.presentation.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.arbelkilani.binge.tv.databinding.FragmentOnBoardingBinding
import com.arbelkilani.binge.tv.presentation.ui.onboarding.adapter.OnBoardingAdapter
import com.arbelkilani.binge.tv.presentation.viewmodel.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFragment : Fragment(), OnPageChangeListener {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    val viewModel: OnBoardingViewModel by viewModels()

    @Inject
    lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.viewPager.apply {
            adapter = onBoardingAdapter
            addOnPageChangeListener(this@OnBoardingFragment)
            onPageSelected(0)
        }
        binding.viewPager.adapter = onBoardingAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        binding.start.isVisible = position == onBoardingAdapter.count - 1
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}