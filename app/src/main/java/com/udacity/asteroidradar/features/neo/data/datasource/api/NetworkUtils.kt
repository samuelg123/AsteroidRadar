package com.udacity.asteroidradar.features.neo.data.datasource.api

import com.squareup.moshi.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.features.neo.data.datasource.api.dto.NeoResponse
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
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

class CustomDate(date: Date) : Date(date.time) {
    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm")
    }

    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    override fun toString(): String = dateFormat.format(this)

}

fun Date.formatted(format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS"): String =
    SimpleDateFormat(format, Locale.getDefault())
        .format(this)

const val SIMPLE_FORMAT = "yyyy-MM-dd"

fun String.toDate(format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS"): Date? =
    SimpleDateFormat(format, Locale.getDefault())
        .parse(this)