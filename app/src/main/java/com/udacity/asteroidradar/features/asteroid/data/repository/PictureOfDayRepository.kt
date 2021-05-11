package com.udacity.asteroidradar.features.asteroid.data.repository

import com.udacity.asteroidradar.features.asteroid.domain.model.PictureOfDay
import com.udacity.asteroidradar.features.asteroid.data.datasource.api.NasaApiService
import com.udacity.asteroidradar.features.asteroid.data.datasource.api.dto.asPreferenceModel
import com.udacity.asteroidradar.features.asteroid.data.entity.asDomainModel
import com.udacity.asteroidradar.features.asteroid.data.datasource.preference.PictureOfDayPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PictureOfDayRepository @Inject constructor(
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