package com.arbelkilani.binge.tv.common.data.repository

import com.arbelkilani.binge.tv.common.source.local.prefsstore.PrefsStore
import com.arbelkilani.binge.tv.common.domain.repository.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor() :
    ConfigurationRepository {

    @Inject
    lateinit var prefsStore: PrefsStore

    override suspend fun isFirstRun(): Flow<Boolean> {
        return prefsStore.isFirstRun()
    }

    override suspend fun setNoFirstRun() {
        prefsStore.setNoFirstRun()
    }
}