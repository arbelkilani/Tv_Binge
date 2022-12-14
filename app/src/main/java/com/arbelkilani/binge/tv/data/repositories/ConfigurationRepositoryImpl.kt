package com.arbelkilani.binge.tv.data.repositories

import com.arbelkilani.binge.tv.data.source.local.prefsstore.PrefsStore
import com.arbelkilani.binge.tv.domain.repositories.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor() : ConfigurationRepository {

    @Inject
    lateinit var prefsStore: PrefsStore

    override fun isFirstRun(): Flow<Boolean> {
        return prefsStore.isFirstRun()
    }
}