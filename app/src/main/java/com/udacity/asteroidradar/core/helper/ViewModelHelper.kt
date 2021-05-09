package com.udacity.asteroidradar.core.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

val ViewModel.coroutineCtx
    get() = viewModelScope.coroutineContext