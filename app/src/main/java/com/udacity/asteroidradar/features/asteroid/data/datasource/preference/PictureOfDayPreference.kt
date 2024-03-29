package com.udacity.asteroidradar.features.asteroid.data.datasource.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.appDataStore
import com.udacity.asteroidradar.features.asteroid.data.entity.PictureOfDayEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject


private val POD = stringPreferencesKey("picture_of_day")

class PictureOfDayPreference @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.appDataStore
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private fun podAdapter() = moshi.adapter(PictureOfDayEntity::class.java)

    /**
     * Move fromJson() to podFromJson() to fix "inappropriate blocking method call" warning.
     * Please see https://stackoverflow.com/a/60136211
     * Idk if this bug or something else, because kotlin [Flow] has already inside background thread.
     */
    private fun podFromJson(jsonString: String) = podAdapter().fromJson(jsonString)

    fun getPictureOfDay(): Flow<PictureOfDayEntity?> {
        return dataStore
            .data
            .map { pref -> pref[POD]?.let { podFromJson(it) } }
    }

    suspend fun setPictureOfDay(pod: PictureOfDayEntity) {
        dataStore.edit { pref ->
            pref[POD] = podAdapter().toJson(pod)
        }
    }

    suspend fun clearPictureOfDay() {
        dataStore.edit { pref ->
            pref.remove(POD)
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