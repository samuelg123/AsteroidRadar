package com.udacity.asteroidradar.features.neo.presentation.viewmodels

import androidx.lifecycle.*
import com.udacity.asteroidradar.core.helper.coroutineCtx
import com.udacity.asteroidradar.core.viewmodel.BaseViewModel
import com.udacity.asteroidradar.core.viewmodel.Status
import com.udacity.asteroidradar.features.neo.domain.interactor.GetListAsteroid
import com.udacity.asteroidradar.features.neo.domain.interactor.GetPictureOfDay
import com.udacity.asteroidradar.features.neo.domain.interactor.RefreshListAsteroid
import com.udacity.asteroidradar.features.neo.domain.interactor.RefreshPictureOfDay
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import com.udacity.asteroidradar.features.neo.domain.model.PictureOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val refreshListAsteroid: RefreshListAsteroid,
    private val refreshPictureOfDay: RefreshPictureOfDay,
    private val getPictureOfDay: GetPictureOfDay,
    private val getListAsteroid: GetListAsteroid,
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

    val pictureOfDay: LiveData<PictureOfDay> by lazy {
        getPictureOfDay().filterNotNull().catch { e ->
            Timber.d("POD Caught $e")
        }.asLiveData(coroutineCtx)
    }

    val asteroids: LiveData<List<Asteroid>> by lazy {
        getListAsteroid().asLiveData(coroutineCtx)
    }

}
