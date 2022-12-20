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
        return ConfigurationEntity(
            url = url,
            backdrop = Image(
                original = backdrops.find { it == ImageSize.ORIGINAL.size },
                small = backdrops.find { it == ImageSize.BACKDROP_W300.size },
                medium = backdrops.find { it == ImageSize.BACKDROP_W780.size },
                large = backdrops.find { it == ImageSize.BACKDROP_W1280.size }
            ),
            logo = Image(
                original = backdrops.find { it == ImageSize.ORIGINAL.size },
                small = backdrops.find { it == ImageSize.LOGO_W154.size },
                medium = backdrops.find { it == ImageSize.LOGO_W185.size },
                large = backdrops.find { it == ImageSize.LOGO_W500.size }
            ),
            poster = Image(
                original = backdrops.find { it == ImageSize.ORIGINAL.size },
                small = backdrops.find { it == ImageSize.POSTER_W185.size },
                medium = backdrops.find { it == ImageSize.POSTER_W342.size },
                large = backdrops.find { it == ImageSize.POSTER_W780.size }
            )
        )
    }
}