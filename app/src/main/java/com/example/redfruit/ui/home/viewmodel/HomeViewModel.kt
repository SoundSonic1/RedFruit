package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.*
import com.example.redfruit.data.api.PostRepository
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.IViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Control logic of the HomeFragment
 */
class HomeViewModel(private val state: SavedStateHandle) : ViewModel(), IViewModel<Collection<Post>> {

    private var limit = 10
    // TODO: save subreddit and sortBy preference to savedstate
    private var subreddit = "grandorder"
    private var sortBy = "new"

    private var _loading = false

    private val _data: MutableLiveData<Collection<Post>> by lazy {
        MutableLiveData<Collection<Post>>().also {
            viewModelScope.launch {
                it.value = get()
            }
        }
    }

    // Encapsulate access to mutable LiveData using backing property
    // with LiveData
    override val data: LiveData<Collection<Post>> get() = _data
    val loading = _loading

    // api call must be in a separat thread
    fun loadData() = viewModelScope.launch {
        // reset limit
        _loading = true
        _data.value = get()
        limit = 10
        _loading = false
    }

    fun loadMoreData(count: Int) = viewModelScope.launch {
        // set hard limit
        if (limit < 70) {
            _loading = true
            limit += count
            _data.value = get()
            _loading = false
        }
    }

    fun saveData() {
       state.set(KEY_SUBREDDIT, subreddit)
    }

    fun getSavedData() = state[KEY_SUBREDDIT] ?: ""

    // call to the api
    private suspend fun get() =
        withContext(Dispatchers.IO) {
            /* perform network IO here */
            PostRepository.getData(url + subreddit + "/" + sortBy + "/.json?limit=" + limit)
        }

    companion object {
        private const val url = "https://www.reddit.com/r/"
        private const val KEY_SUBREDDIT = "KEY_SUBREDDIT"
    }
}