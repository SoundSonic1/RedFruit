package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.api.PostRepository
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.IViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel(), IViewModel<Post> {
    private val _data: MutableLiveData<Collection<Post>> by lazy {
        MutableLiveData<Collection<Post>>().also {
            loadData()
        }
    }

    // Encapsulate access to mutable LiveData using backing property
    // with LiveData
    override val data: LiveData<Collection<Post>> = _data

    // api call must be in a separat thread
    fun loadData() = viewModelScope.launch {
        _data.value = get()
    }

    fun update(posts: Collection<Post>) {
        _data.value = posts
    }

    // call to the api
    private suspend fun get() =
        withContext(Dispatchers.IO) {
            /* perform network IO here */
            PostRepository.getData()
        }
}