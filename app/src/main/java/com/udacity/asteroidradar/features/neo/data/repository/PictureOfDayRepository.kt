package com.udacity.asteroidradar.features.neo.data.repository

import com.udacity.asteroidradar.features.neo.domain.model.PictureOfDay
import com.udacity.asteroidradar.features.neo.data.datasource.api.NasaApiService
import com.udacity.asteroidradar.features.neo.data.datasource.api.dto.asPreferenceModel
import com.udacity.asteroidradar.features.neo.data.entity.asDomainModel
import com.udacity.asteroidradar.features.neo.data.datasource.preference.PictureOfDayPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PictureOfDayRepository(
    private val preference: PictureOfDayPreference,
    private val api: NasaApiService,
) {

    /**
     *
     */
    val pictureOfDay: Flow<PictureOfDay?> =
        preference.getPictureOfDay().map {
            it?.asDomainModel()
        }

    /**
     *
     */
    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            val response = api.getPictureOfDay()
            preference.setPictureOfDay(response.asPreferenceModel())
        }
    }
}