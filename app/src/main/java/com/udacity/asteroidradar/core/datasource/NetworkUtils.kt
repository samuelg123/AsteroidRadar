package com.udacity.asteroidradar.core.datasource

import com.squareup.moshi.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.features.asteroid.data.datasource.api.dto.NeoResponse
import com.udacity.asteroidradar.features.asteroid.domain.model.Asteroid
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.time.temporal.TemporalAccessor
import java.util.*
import kotlin.collections.ArrayList


/**
 * Replaced by [NeoResponse]
 */
fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")

    val asteroidList = ArrayList<Asteroid>()

    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
    for (formattedDate in nextSevenDaysFormattedDates) {
        val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

        for (i in 0 until dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")

            val closeApproachData = asteroidJson
                .getJSONArray("close_approach_data").getJSONObject(0)
            val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                .getDouble("kilometers_per_second")
            val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                .getDouble("astronomical")
            val isPotentiallyHazardous = asteroidJson
                .getBoolean("is_potentially_hazardous_asteroid")

            val asteroid = Asteroid(
                id, codename, formattedDate, absoluteMagnitude,
                estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous
            )
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}

private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}

class CustomDateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                dateFormat.parse(dateAsString)
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(dateFormat) {
                writer.value(value.toString())
            }
        }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm")
    }

}

fun String.toDate(): Date {
    val ta: TemporalAccessor = DateTimeFormatter.ISO_INSTANT.parse(this)
    val i: Instant = Instant.from(ta)
    return Date.from(i)
}

fun String.toDate(format: String): Date {
    val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern(format)
            .withResolverStyle(ResolverStyle.SMART)
    val dt = LocalDate.parse(this, formatter).atStartOfDay()
    return Date.from(dt.toInstant(ZoneOffset.UTC))
}

fun Date.toIso8601String(): String =
    DateTimeFormatter.ISO_DATE_TIME
        .withLocale(Locale.getDefault())
        .withZone(ZoneOffset.UTC)
        .format(this.toInstant())

fun Date.formatted(format: String): String =
    DateTimeFormatter.ofPattern(format)
        .withLocale(Locale.getDefault())
        .withZone(ZoneOffset.UTC)
        .format(this.toInstant())

//fun Date.formatted(format: String = ISO8601_FORMAT): String =
//    SimpleDateFormat(format, Locale.getDefault())
//        .format(this)

//const val ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"
//const val SIMPLE_FORMAT = "yyyy-MM-dd"

//fun String.toDate(format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS"): Date? =
//    SimpleDateFormat(format, Locale.getDefault())
//        .parse(this)