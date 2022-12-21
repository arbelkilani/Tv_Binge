package com.arbelkilani.binge.tv.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null

    val binding: B
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvents()
        initViews()
        initViewModelObservation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = bindView(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): B

    open fun initViewModelObservation() {}

    open fun initEvents() {}

    open fun initViews() {}
}