package com.arbelkilani.binge.tv.feature.home

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entity.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import com.arbelkilani.binge.tv.feature.home.presentation.HomeFragmentDirections
import javax.inject.Inject

class HomeNavigator @Inject constructor() : HomeContract.ViewNavigation {

    override fun navigateToTvDetails(fragment: Fragment, tv: Tv) {
        Navigation.findNavController(
            fragment.requireActivity(),
            R.id.nav_host_fragment_activity_main
        ).navigate(
            HomeFragmentDirections.actionHomeFragmentToTvDetailsFragment2(tv)
        )
    }

    override fun navigateToShowsFromProvider(
        fragment: Fragment,
        watchProviderEntity: WatchProviderEntity
    ) {
        TODO("Not yet implemented")
    }

    override fun navigateToShowsFromGenre(fragment: Fragment, genreEntity: GenreEntity) {
        TODO("Not yet implemented")
    }

}