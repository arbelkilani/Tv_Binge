package com.arbelkilani.binge.tv.common.source.local.prefsstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsStoreImpl @Inject constructor(
    @ApplicationContext val context: Context
) : PrefsStore {

    companion object {
        private const val STORE_NAME = "com.arbelkilani.binge.tv.data.store"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = STORE_NAME
    )

    private object PreferencesKeys {
        val IS_FIRST_RUN_KEY = booleanPreferencesKey("is_first_run_key")
    }

    override fun isFirstRun() =
        context.dataStore.data.catch { exception ->
            emit(emptyPreferences())
            exception.printStackTrace()
        }.map { preferences ->
            preferences[PreferencesKeys.IS_FIRST_RUN_KEY] ?: true
        }

    override suspend fun setNoFirstRun() {
        try {
            context.dataStore.edit { mutablePreferences ->
                mutablePreferences[PreferencesKeys.IS_FIRST_RUN_KEY] =
                    mutablePreferences[PreferencesKeys.IS_FIRST_RUN_KEY] == null
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}