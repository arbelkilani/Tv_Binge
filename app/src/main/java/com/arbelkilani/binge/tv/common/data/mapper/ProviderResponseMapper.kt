package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.enum.ImageSize
import com.arbelkilani.binge.tv.common.data.response.ProviderMap
import com.arbelkilani.binge.tv.common.data.response.ProviderResponse
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.domain.usecase.GetImageUseCase
import com.arbelkilani.binge.tv.feature.home.data.request.DiscoverQuery
import javax.inject.Inject

class ProviderResponseMapper @Inject constructor() {

    @Inject
    lateinit var getImageUseCase: GetImageUseCase

    suspend fun map(providerResponse: ProviderMap): List<ProviderEntity> {
        val result = mutableMapOf<String, List<ProviderResponse>>()
        providerResponse.flatrate?.let {
            result.put(
                DiscoverQuery.MonetizationType.FLAT_RATE.value,
                it
            )
        }
        providerResponse.buy?.let { result.put(DiscoverQuery.MonetizationType.BUY.value, it) }
        providerResponse.rent?.let { result.put(DiscoverQuery.MonetizationType.RENT.value, it) }
        /*val free =
            providerResponse.free?.filterNot { providerResponse.flatrate?.contains(it) == true }
        free?.map {
            Log.i("TAG**", "free : $it")
        }*/
        providerResponse.free?.let { result.put(DiscoverQuery.MonetizationType.FREE.value, it) }
        providerResponse.ads?.let { result.put(DiscoverQuery.MonetizationType.ADS.value, it) }


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