package com.udacity.asteroidradar.features.neo.domain.interactor

import com.udacity.asteroidradar.core.interactor.UseCase
import com.udacity.asteroidradar.features.neo.data.repository.PictureOfDayRepository
import com.udacity.asteroidradar.features.neo.domain.model.PictureOfDay
import kotlinx.coroutines.flow.Flow

//
//class GetPictureOfDay(private val repository: PictureOfDayRepository) :
//    UseCase<Flow<PictureOfDay?>>() {
//    override fun execute(): Flow<PictureOfDay?> = repository.pictureOfDay
//}


class GetPictureOfDay(private val repository: PictureOfDayRepository) : UseCase() {
    operator fun invoke(): Flow<PictureOfDay?> = repository.pictureOfDay
}