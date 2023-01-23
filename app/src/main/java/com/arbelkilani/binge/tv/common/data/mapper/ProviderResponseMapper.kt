package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.data.response.ProviderMap
import com.arbelkilani.binge.tv.common.data.response.ProviderResponse
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.usecase.GetImageUseCase
import javax.inject.Inject

class ProviderResponseMapper @Inject constructor() {

    @Inject
    lateinit var getImageUseCase: GetImageUseCase

    suspend fun map(providerResponse: ProviderMap): List<ProviderEntity> {
        val result = mutableMapOf<String, List<ProviderResponse>>()
        providerResponse.flatrate?.let { result.put("flatrate", it) }
        providerResponse.buy?.let { result.put("buy", it) }
        providerResponse.rent?.let { result.put("rent", it) }
        providerResponse.free?.let { result.put("free", it) }

        return result.flatMap { map ->
            map.value.map { response ->
                ProviderEntity(
                    id = response.providerId,
                    name = response.providerName,
                    logo = getImageUseCase.invoke(response.logo, ImageSize.LOGO_W154),
                    type = map.key,
                    link = providerResponse.link
                )
            }
        }
    }
}