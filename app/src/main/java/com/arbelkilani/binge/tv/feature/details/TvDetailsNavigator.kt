package com.arbelkilani.binge.tv.feature.details

import androidx.navigation.fragment.findNavController
import com.arbelkilani.binge.tv.feature.details.presentation.TvDetailsFragment

class TvDetailsNavigator : TvDetailsContract.ViewNavigation {

    override fun onBackPressed(fragment: TvDetailsFragment) {
        fragment.findNavController().popBackStack()
    }
}