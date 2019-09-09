package com.example.redfruit.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redfruit.ui.base.IViewModel

/**
 * ViewModel used for communication between Fragments and Activity
 * For now, we specify the type at compile time
 */
class SubredditViewModel : ViewModel(), IViewModel<String> {

    private val _data = MutableLiveData<String>("")

    override val data: LiveData<String> get() = _data

    fun setSub(sub: String) {
        _data.value = sub
    }
}