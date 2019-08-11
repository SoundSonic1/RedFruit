package com.example.redfruit.ui.base

import androidx.lifecycle.LiveData

/**
 * All our ViewModels should implement this
 * @property data observable data holder
 */
interface IViewModel<T> {
    // only expose immutable LiveData
    val data: LiveData<T>
}