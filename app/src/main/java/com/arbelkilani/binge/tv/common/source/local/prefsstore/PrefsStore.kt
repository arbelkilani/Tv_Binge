package com.arbelkilani.binge.tv.common.source.local.prefsstore

import kotlinx.coroutines.flow.Flow


interface PrefsStore {
    fun isFirstRun(): Flow<Boolean>
    suspend fun setNoFirstRun()
}