package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.api.OpenApi
import com.example.redfruit.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel()  {
    private val data: MutableLiveData<List<Post>> = MutableLiveData()

    fun getData(): MutableLiveData<List<Post>> {
        loadData()
        return data
    }

    // api call must be in a separat thread
    private fun loadData() = viewModelScope.launch {
        data.value = get()
    }

    // call to the api
    private suspend fun get() =
        withContext(Dispatchers.IO) {
            /* perform network IO here */
            val openApi = OpenApi()
            openApi.getPosts()
        }
}