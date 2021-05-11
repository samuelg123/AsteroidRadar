package com.udacity.asteroidradar.features.asteroid.data.datasource.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.features.asteroid.data.datasource.api.toDate
import com.udacity.asteroidradar.features.asteroid.data.entity.AsteroidEntity
import com.udacity.asteroidradar.features.asteroid.domain.model.Asteroid
import java.time.format.DateTimeFormatter

@JsonClass(generateAdapter = true)
data class NeoResponse(
    val links: NeoResponseLinks,

    @Json(name = "element_count")
    val elementCount: Long,

    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<NearEarthObject>>
)

@JsonClass(generateAdapter = true)
data class NeoResponseLinks(
    val next: String,
    val prev: String,
    val self: String
)

@JsonClass(generateAdapter = true)
data class NearEarthObject(
    val links: NearEarthObjectLinks,
    val id: String,

    @Json(name = "neo_reference_id")
    val neoReferenceID: String,

    val name: String,

    @Json(name = "nasa_jpl_url")
    val nasaJplURL: String,

    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitudeH: Double,

    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,

    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,

    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachDatum>,

    @Json(name = "is_sentry_object")
    val isSentryObject: Boolean
)

@JsonClass(generateAdapter = true)
data class CloseApproachDatum(
    @Json(name = "close_approach_date")
    val closeApproachDate: String,

    @Json(name = "close_approach_date_full")
    val closeApproachDateFull: String,

    @Json(name = "epoch_date_close_approach")
    val epochDateCloseApproach: Long,

    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity,

    @Json(name = "miss_distance")
    val missDistance: MissDistance,

    @Json(name = "orbiting_body")
    val orbitingBody: String
)

@JsonClass(generateAdapter = true)
data class MissDistance(
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)

@JsonClass(generateAdapter = true)
data class RelativeVelocity(
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: String,

    @Json(name = "kilometers_per_hour")
    val kilometersPerHour: String,

    @Json(name = "miles_per_hour")
    val milesPerHour: String
)

@JsonClass(generateAdapter = true)
data class EstimatedDiameter(
    val kilometers: Feet,
    val meters: Feet,
    val miles: Feet,
    val feet: Feet
)

@JsonClass(generateAdapter = true)
data class Feet(
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double,

    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double
)

@JsonClass(generateAdapter = true)
data class NearEarthObjectLinks(
    val self: String
)

fun List<NearEarthObject>.asDomainModel(): List<Asteroid> {
    return map {
        val closeApproach = it.closeApproachData.first()
        Asteroid(
            id = it.id.toLong(),
            codename = it.name,
            closeApproachDate = closeApproach.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitudeH,
            estimatedDiameter = it.estimatedDiameter.kilometers.estimatedDiameterMax,
            relativeVelocity = closeApproach.relativeVelocity.kilometersPerSecond.toDouble(),
            distanceFromEarth = closeApproach.missDistance.astronomical.toDouble(),
            isPotentiallyHazardous = it.isPotentiallyHazardousAsteroid,
        )
    }
}

fun List<NearEarthObject>.asDatabaseModel(): Array<AsteroidEntity> {
    return map {
        val closeApproach = it.closeApproachData.first()
        AsteroidEntity(
            id = it.id.toLong(),
            codename = it.name,
            closeApproachDate = closeApproach.closeApproachDate.toDate(format = "yyyy-MM-dd"),
            absoluteMagnitude = it.absoluteMagnitudeH,
            estimatedDiameter = it.estimatedDiameter.kilometers.estimatedDiameterMax,
            relativeVelocity = closeApproach.relativeVelocity.kilometersPerSecond.toDouble(),
            distanceFromEarth = closeApproach.missDistance.astronomical.toDouble(),
            isPotentiallyHazardous = it.isPotentiallyHazardousAsteroid,
        )
    }.toTypedArray()
}
