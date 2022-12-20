package com.arbelkilani.binge.tv.data.mapper

import com.arbelkilani.binge.tv.data.entities.ConfigurationResponse
import com.arbelkilani.binge.tv.data.enum.ImageSize
import com.arbelkilani.binge.tv.domain.entities.ConfigurationEntity
import com.arbelkilani.binge.tv.domain.entities.Image
import javax.inject.Inject

class ConfigurationMapper @Inject constructor() {
    fun map(configuration: ConfigurationResponse): ConfigurationEntity {
        val url = configuration.imagesConfiguration.secureBaseUrl
        val backdrops = configuration.imagesConfiguration.backdropSize
        val logos = configuration.imagesConfiguration.logoSize
        val posters = configuration.imagesConfiguration.posterSize
        return ConfigurationEntity(
            url = url,
            backdrop = Image(
                original = url + backdrops.find { it == ImageSize.ORIGINAL.size },
                small = url + backdrops.find { it == ImageSize.BACKDROP_W300.size },
                medium = url + backdrops.find { it == ImageSize.BACKDROP_W780.size },
                large = url + backdrops.find { it == ImageSize.BACKDROP_W1280.size }
            ),
            logo = Image(
                original = url + logos.find { it == ImageSize.ORIGINAL.size },
                small = url + logos.find { it == ImageSize.LOGO_W154.size },
                medium = url + logos.find { it == ImageSize.LOGO_W185.size },
                large = url + logos.find { it == ImageSize.LOGO_W500.size }
            ),
            poster = Image(
                original = url + posters.find { it == ImageSize.ORIGINAL.size },
                small = url + posters.find { it == ImageSize.POSTER_W185.size },
                medium = url + posters.find { it == ImageSize.POSTER_W342.size },
                large = url + posters.find { it == ImageSize.POSTER_W780.size }
            )
        )
    }
}