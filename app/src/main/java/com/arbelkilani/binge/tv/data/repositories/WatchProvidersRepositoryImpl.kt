package com.arbelkilani.binge.tv.data.repositories

import android.util.Log
import com.arbelkilani.binge.tv.data.mapper.WatchProviderMapper
import com.arbelkilani.binge.tv.data.source.local.room.AppDatabase
import com.arbelkilani.binge.tv.data.source.remote.ApiService
import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.domain.repositories.WatchProvidersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class WatchProvidersRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val database: AppDatabase
) : WatchProvidersRepository {

    @Inject
    lateinit var mapper: WatchProviderMapper

    override suspend fun getProviders(): Flow<List<WatchProviderEntity>> {
        val baseLogoUrl = database.configurationDao().get()?.logo?.medium
        return flow {
            emit(service.getProviders(Locale.getDefault().country).providers.map {
                mapper.map(it.copy(logoPath = baseLogoUrl + it.logoPath), false)
            })
        }
    }

    override fun saveSelectedWatchProviders(providersId: MutableList<WatchProviderEntity>) {
        Log.i("TAG**", "selected providers = $providersId")
    }
}