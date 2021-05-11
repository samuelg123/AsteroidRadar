package com.udacity.asteroidradar.features.asteroid.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.features.asteroid.domain.model.PictureOfDay

@JsonClass(generateAdapter = true)
data class PictureOfDayEntity(
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
)

fun PictureOfDayEntity.asDomainModel() =
    PictureOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )

