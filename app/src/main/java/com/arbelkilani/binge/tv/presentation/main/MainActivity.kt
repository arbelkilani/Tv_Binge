package com.arbelkilani.binge.tv.presentation.main

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()
    var contentHasLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadContent()
    }

    private fun loadContent() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        lifecycleScope.launch {
            viewModel.isFirstRun.collectLatest {
                if (it) {
                    navController.setGraph(R.navigation.walkthrough_navigation)
                } else {
                    navController.setGraph(R.navigation.dashboard_navigation)
                }
            }
        }.apply {
            contentHasLoaded = true
            setupSplashScreen()
        }
    }

    private fun setupSplashScreen() {
        val content: View = binding.root
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }
}