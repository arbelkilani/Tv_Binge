package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.response.ConfigurationResponse
import com.arbelkilani.binge.tv.common.domain.entity.ConfigurationEntity
import javax.inject.Inject

class ConfigurationResponseMapper @Inject constructor() {
    fun map(configuration: ConfigurationResponse) = ConfigurationEntity(
        url = configuration.imagesConfiguration.secureBaseUrl
    )
}