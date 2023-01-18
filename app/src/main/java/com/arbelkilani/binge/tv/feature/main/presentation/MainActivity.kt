package com.arbelkilani.binge.tv.feature.main.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.Navigation
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val controller = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        val state = controller.takeUnless { it.previousBackStackEntry == null }
            ?.popBackStack()
            ?.let { true }
            ?: false
        if (state.not())
            super.onBackPressed()
    }
}