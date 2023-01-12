package com.arbelkilani.binge.tv.feature.main.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splash.setKeepOnScreenCondition { true }
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        lifecycleScope.launch {
            viewModel.isFirstRun.collectLatest {
                if (it) {
                    navController.setGraph(R.navigation.walkthrough_navigation)
                } else {
                    navController.setGraph(R.navigation.dashboard_navigation)
                }
                delay(600)
                splash.setKeepOnScreenCondition { false }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val controller = Navigation.findNavController(this, R.id.nav_host_home)
        val state = controller.takeUnless { it.previousBackStackEntry == null }
            ?.popBackStack()
            ?.let { true }
            ?: false
        if (state.not())
            super.onBackPressed()
    }
}