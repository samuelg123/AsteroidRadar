package com.udacity.asteroidradar.features.neo.data.datasource.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.features.neo.data.datasource.api.dto.NeoResponse
import com.udacity.asteroidradar.features.neo.data.datasource.api.dto.PictureOfDayResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date
import java.util.concurrent.TimeUnit

// I'm using https://github.com/google/secrets-gradle-plugin
// Put your nasa api key inside local.properties as follows:
//
// ...
// nasaApiKey=<your api key>
//
interface NasaApiService {

    // Query parameter "api_key" was added inside AuthInterceptor.kt
    @GET("/neo/rest/v1/feed")
    fun getNeo(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Deferred<NeoResponse>

    // Query parameter "api_key" was added inside AuthInterceptor.kt
    @GET("/planetary/apod")
    suspend fun getPictureOfDay(): PictureOfDayResponse
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(CustomDateAdapter())
    .build()

private fun buildClient() = OkHttpClient
    .Builder()
    .addInterceptor(AuthInterceptor())
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .readTimeout(30, TimeUnit.SECONDS) // socket timeout
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(buildClient())
    .baseUrl(BASE_URL)
    .build()

object NasaApi {
    val retrofitService: NasaApiService by lazy { retrofit.create(NasaApiService::class.java) }
}
