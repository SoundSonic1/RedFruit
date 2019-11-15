package com.example.redfruit.ui.base

import androidx.lifecycle.LiveData

/**
 * All our ViewModels should implement this
 * @property data observable data holder, immutable
 * @param T data type which is exposed
 */
interface ILiveData<T> {
    val data: LiveData<T>
}
