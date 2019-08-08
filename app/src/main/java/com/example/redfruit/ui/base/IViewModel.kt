package com.example.redfruit.ui.base

import androidx.lifecycle.LiveData

interface IViewModel<T> {
    // only expose immutable LiveData
    val data: LiveData<Collection<T>>
}