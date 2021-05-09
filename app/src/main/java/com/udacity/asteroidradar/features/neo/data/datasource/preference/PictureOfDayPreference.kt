package com.udacity.asteroidradar.features.neo.data.datasource.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.appDataStore
import com.udacity.asteroidradar.features.neo.data.entity.PictureOfDayEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val POD = stringPreferencesKey("picture_of_day")

class PictureOfDayPreference(context: Context) {
    private val dataStore: DataStore<Preferences> = context.appDataStore
    private val moshi = Moshi.Builder().build()
    private val podAdapter = moshi.adapter(PictureOfDayEntity::class.java)

    fun getPictureOfDay(): Flow<PictureOfDayEntity?> = dataStore.data
        .map { pref ->
            pref[POD]?.let {
                podAdapter.fromJson(it) //TODO: WARNING
            }
        }

    suspend fun setPOD(pod: PictureOfDayEntity) {
        dataStore.edit { pref ->
            pref[POD] = podAdapter.toJson(pod)
        }
    }
}