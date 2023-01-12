package com.arbelkilani.binge.tv.common.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null

    val binding: B
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(this.javaClass.simpleName, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        Log.i(this.javaClass.simpleName, "onResume")
        super.onResume()
        lifecycleScope.launch(Dispatchers.Default) {
            initViewModelObservation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(this.javaClass.simpleName, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        initEvents()
        initViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.i(this.javaClass.simpleName, "onCreateView")
        _binding = bindView(inflater, container)
        return binding.root
    }

    override fun onDestroy() {
        Log.i(this.javaClass.simpleName, "onDestroy")
        super.onDestroy()
    }

    override fun onDestroyView() {
        Log.i(this.javaClass.simpleName, "onDestroyView")
        _binding = null
        super.onDestroyView()
    }

    abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): B

    open suspend fun initViewModelObservation() {}

    open fun initEvents() {}

    open fun initViews() {}
}