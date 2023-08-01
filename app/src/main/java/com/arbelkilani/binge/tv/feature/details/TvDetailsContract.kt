package com.arbelkilani.binge.tv.feature.details

import com.arbelkilani.binge.tv.common.presentation.model.Person
import com.arbelkilani.binge.tv.feature.details.presentation.TvDetailsFragment
import com.arbelkilani.binge.tv.feature.details.presentation.entities.ContentRating
import com.arbelkilani.binge.tv.feature.details.presentation.entities.ExternalId
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import com.arbelkilani.binge.tv.feature.details.presentation.entities.TvDetails

class TvDetailsContract {
    interface ViewCapabilities {
        suspend fun details(data: TvDetails)
        suspend fun keywords(data: List<Keywords>)
        suspend fun casts(data: List<Person>)
        suspend fun showExternalId(data: ExternalId)
        suspend fun showContentRating(data: ContentRating)
    }

    interface ViewNavigation {
        fun onBackPressed(fragment: TvDetailsFragment)
    }
}