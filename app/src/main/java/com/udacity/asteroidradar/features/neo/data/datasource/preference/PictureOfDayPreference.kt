package com.udacity.asteroidradar.features.neo.data.datasource.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.appDataStore
import com.udacity.asteroidradar.features.neo.data.entity.PictureOfDayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


private val POD = stringPreferencesKey("picture_of_day")

class PictureOfDayPreference private constructor(context: Context) {

    private val dataStore: DataStore<Preferences> = context.appDataStore
    private val moshi = Moshi.Builder().build()
    private val podAdapter = moshi.adapter(PictureOfDayEntity::class.java)

    fun getPictureOfDay(): Flow<PictureOfDayEntity?> {
        return dataStore
            .data
            .map { pref -> pref[POD]?.let { podAdapter.fromJson(it) } }
    }

    suspend fun setPictureOfDay(pod: PictureOfDayEntity) {
        dataStore.edit { pref ->
            pref[POD] = podAdapter.toJson(pod)
        }
    }

    companion object {
        @Volatile
        private var instance: PictureOfDayPreference? = null

        fun getInstance(context: Context): PictureOfDayPreference {
            return instance ?: synchronized(this) {
                instance ?: PictureOfDayPreference(context).also { instance = it }
            }
        }
    }
}