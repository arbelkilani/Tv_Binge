package com.arbelkilani.binge.tv.feature.home

import androidx.fragment.app.Fragment
import com.arbelkilani.binge.tv.common.domain.entities.GenreEntity
import com.arbelkilani.binge.tv.common.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Tv

interface HomeContract {

    interface ViewCapabilities {
        fun setNoFirstRun()
    }

    interface ViewNavigation {
        fun navigateToTvDetails(fragment: Fragment, tv: Tv)
        fun navigateToShowsFromProvider(
            fragment: Fragment,
            watchProviderEntity: WatchProviderEntity
        )

        fun navigateToShowsFromGenre(fragment: Fragment, genreEntity: GenreEntity)
    }
}