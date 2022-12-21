package com.arbelkilani.binge.tv.presentation.walkthrough.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Inject

class WalkthroughPagerAdapter @Inject constructor(
    private val fragments: List<Fragment>,
    parentFragmentManager: FragmentManager,
    val lifecycle: Lifecycle
) : FragmentStateAdapter(parentFragmentManager, lifecycle) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}