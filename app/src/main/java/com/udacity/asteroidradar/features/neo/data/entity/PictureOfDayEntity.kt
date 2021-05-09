package com.udacity.asteroidradar.features.neo.data.entity

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.features.neo.domain.model.PictureOfDay

@JsonClass(generateAdapter = true)
data class PictureOfDayEntity(
    val mediaType: String,
    val title: String,
    val url: String
)

fun PictureOfDayEntity.asDomainModel() =
    PictureOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )

