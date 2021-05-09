package com.udacity.asteroidradar.features.neo.data.datasource.api.dto

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.features.neo.data.entity.AsteroidEntity
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid


@JsonClass(generateAdapter = true)
data class NeoResponse(
    val links: NeoResponseLinks,
    val elementCount: Long,
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
    val neoReferenceID: String,
    val name: String,
    val nasaJplURL: String,
    val absoluteMagnitudeH: Double,
    val estimatedDiameter: EstimatedDiameter,
    val isPotentiallyHazardousAsteroid: Boolean,
    val closeApproachData: List<CloseApproachDatum>,
    val isSentryObject: Boolean
)

@JsonClass(generateAdapter = true)
data class CloseApproachDatum(
    val closeApproachDate: String,
    val closeApproachDateFull: String,
    val epochDateCloseApproach: Long,
    val relativeVelocity: RelativeVelocity,
    val missDistance: MissDistance,
    val orbitingBody: OrbitingBody
)

@JsonClass(generateAdapter = true)
data class MissDistance(
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)

@JsonClass(generateAdapter = true)
enum class OrbitingBody(val value: String) {
    Earth("Earth");

    companion object {
        public fun fromValue(value: String): OrbitingBody = when (value) {
            "Earth" -> Earth
            else -> throw IllegalArgumentException()
        }
    }
}

@JsonClass(generateAdapter = true)
data class RelativeVelocity(
    val kilometersPerSecond: String,
    val kilometersPerHour: String,
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
    val estimatedDiameterMin: Double,
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
            closeApproachDate = closeApproach.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitudeH,
            estimatedDiameter = it.estimatedDiameter.kilometers.estimatedDiameterMax,
            relativeVelocity = closeApproach.relativeVelocity.kilometersPerSecond.toDouble(),
            distanceFromEarth = closeApproach.missDistance.astronomical.toDouble(),
            isPotentiallyHazardous = it.isPotentiallyHazardousAsteroid,
        )
    }.toTypedArray()
}
