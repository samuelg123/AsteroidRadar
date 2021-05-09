package com.udacity.asteroidradar.features.neo.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.udacity.asteroidradar.core.helper.coroutineCtx
import com.udacity.asteroidradar.features.neo.domain.interactor.GetListAsteroid
import com.udacity.asteroidradar.features.neo.domain.interactor.GetPictureOfDay
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import com.udacity.asteroidradar.features.neo.domain.model.PictureOfDay

class MainViewModel(
    private val getPictureOfDay: GetPictureOfDay,
    private val getListAsteroid: GetListAsteroid
) : ViewModel() {

    val pictureOfDay: LiveData<PictureOfDay?> by lazy {
        getPictureOfDay().asLiveData(coroutineCtx)
    }

    val asteroids: LiveData<List<Asteroid>> by lazy {
        getListAsteroid().asLiveData(coroutineCtx)
    }

}
