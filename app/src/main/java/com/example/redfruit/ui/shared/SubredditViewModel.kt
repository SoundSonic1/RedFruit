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
    private val _imageUrl = MutableLiveData<String>("https://wallpaperaccess.com/full/17520.jpg")

    override val data: LiveData<String> get() = _data
    val imageUrl: LiveData<String> get() = _imageUrl

    fun setSub(sub: String) {
        _data.value = sub.toLowerCase(Locale.ENGLISH)
    }
}