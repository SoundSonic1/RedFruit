package com.example.redfruit.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redfruit.ui.base.IViewModel
import java.util.*

/**
 * ViewModel used for communication between Fragments and Activity
 * For now, we specify the type at compile time
 */
class SubredditViewModel(subreddit: String) : ViewModel(), IViewModel<String> {

    private val _data = MutableLiveData<String>(subreddit)

    override val data: LiveData<String> get() = _data

    fun setSub(sub: String) {
        _data.value = sub.toLowerCase(Locale.ENGLISH)
    }
}