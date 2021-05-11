package com.udacity.asteroidradar

import android.content.Context
import com.udacity.asteroidradar.features.asteroid.data.datasource.api.NasaApi
import com.udacity.asteroidradar.features.asteroid.data.datasource.preference.PictureOfDayPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providePictureOfDayPreference(@ApplicationContext context: Context) = PictureOfDayPreference.getInstance(context)

    @Singleton
    @Provides
    fun provideNasaApiService() = NasaApi.retrofitService

}