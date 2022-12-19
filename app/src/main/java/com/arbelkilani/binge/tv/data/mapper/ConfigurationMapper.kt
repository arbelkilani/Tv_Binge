package com.arbelkilani.binge.tv.data.mapper

import com.arbelkilani.binge.tv.data.entities.ConfigurationResponse
import com.arbelkilani.binge.tv.domain.entities.ConfigurationEntity
import javax.inject.Inject

class ConfigurationMapper @Inject constructor() {
    fun map(configuration: ConfigurationResponse) = ConfigurationEntity(
        url = configuration.imagesConfiguration.secureBaseUrl,
        logoSizes = configuration.imagesConfiguration.logoSize
    )
}