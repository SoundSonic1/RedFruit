package com.example.redfruit.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.IViewModel

/**
 * ViewModel used for communication between Fragments and Activity
 * For now, we specify the type at compile time
 */
class PostSharedViewModel : ViewModel(), IViewModel<Post> {

    private val _data = MutableLiveData<Collection<Post>>()

    override val data: LiveData<Collection<Post>> = _data

    fun setData(msg: Post) {
        _data.value = setOf(msg)
    }

    fun setData(msg: Collection<Post>) {
        _data.value = msg
    }
}