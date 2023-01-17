package com.arbelkilani.binge.tv.feature.home

import androidx.fragment.app.Fragment
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv

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