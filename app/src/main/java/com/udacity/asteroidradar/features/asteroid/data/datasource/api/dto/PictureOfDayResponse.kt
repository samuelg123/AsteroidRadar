package com.udacity.asteroidradar.features.asteroid.data.datasource.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.features.asteroid.data.entity.PictureOfDayEntity

@JsonClass(generateAdapter = true)
data class PictureOfDayResponse(
    val date: String,
    val explanation: String,
    val hdurl: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "service_version") val serviceVersion: String,
    val title: String,
    val url: String
)

fun PictureOfDayResponse.asPreferenceModel(): PictureOfDayEntity {
    return PictureOfDayEntity(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}
