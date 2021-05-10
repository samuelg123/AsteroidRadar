package com.udacity.asteroidradar.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class Status { INIT, LOADING, ERROR, DONE }
open class BaseViewModel : ViewModel() {
    protected val viewModelStatus = MutableLiveData(Status.INIT)

    val status : LiveData<Status>
        get() = viewModelStatus

}