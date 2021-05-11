package com.udacity.asteroidradar.core.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.AsteroidApplication
import javax.inject.Inject

enum class Status { INIT, LOADING, ERROR, DONE }
open class BaseViewModel : ViewModel() {
    protected val viewModelStatus = MutableLiveData(Status.INIT)

    val status : LiveData<Status>
        get() = viewModelStatus

}