package com.udacity.asteroidradar.features.neo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.features.neo.data.datasource.api.SIMPLE_FORMAT
import com.udacity.asteroidradar.features.neo.data.datasource.api.formatted
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import java.util.*

@Entity(tableName = "asteroid")
data class AsteroidEntity(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: Date?,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate?.formatted(SIMPLE_FORMAT) ?: "-",
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
