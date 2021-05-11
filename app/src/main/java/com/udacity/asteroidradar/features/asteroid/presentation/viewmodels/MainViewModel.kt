package com.udacity.asteroidradar.features.asteroid.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.udacity.asteroidradar.AsteroidApplication
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.core.helper.coroutineCtx
import com.udacity.asteroidradar.core.viewmodel.BaseViewModel
import com.udacity.asteroidradar.core.viewmodel.Status
import com.udacity.asteroidradar.features.asteroid.domain.interactor.GetListAsteroid
import com.udacity.asteroidradar.features.asteroid.domain.interactor.GetPictureOfDay
import com.udacity.asteroidradar.features.asteroid.domain.interactor.RefreshListAsteroid
import com.udacity.asteroidradar.features.asteroid.domain.interactor.RefreshPictureOfDay
import com.udacity.asteroidradar.features.asteroid.domain.model.Asteroid
import com.udacity.asteroidradar.features.asteroid.domain.model.PictureOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    // Remove var/val to avoid context memory leaks.
    // https://stackoverflow.com/a/66328757 and https://stackoverflow.com/a/45822035
    @ApplicationContext context: Context,
    val refreshListAsteroid: RefreshListAsteroid,
    val refreshPictureOfDay: RefreshPictureOfDay,
    val getPictureOfDay: GetPictureOfDay,
    val getListAsteroid: GetListAsteroid,
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            try {
                viewModelStatus.value = Status.LOADING
                refreshPictureOfDay()
                refreshListAsteroid()
                viewModelStatus.value = Status.DONE
            } catch (e: Exception) {
                viewModelStatus.value = Status.ERROR
                Timber.d("Refresh Caught $e")
            }
        }
    }

    val pictureOfDay: LiveData<PictureOfDay?> by lazy {
        getPictureOfDay().catch { e ->
            Timber.d("POD Caught $e")
        }.asLiveData(coroutineCtx)
    }

    val pictureOfDayTitle: LiveData<String> by lazy {
        pictureOfDay.map {
            if (it == null) {
                context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
            } else {
                context.getString(
                    R.string.nasa_picture_of_day_content_description_format,
                    it.title
                )
            }
        }
    }

    val asteroids: LiveData<List<Asteroid>> by lazy {
        getListAsteroid().asLiveData(coroutineCtx)
    }

}
