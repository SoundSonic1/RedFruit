package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.*
import com.example.redfruit.data.api.IRepository
import com.example.redfruit.data.api.PostRepository
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.IViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Control logic of the HomeFragment
 */
class HomeViewModel(
    private val state: SavedStateHandle
) : ViewModel(), IViewModel<List<Post>> {

    // TODO: inject repo into constructor
    private val repo: IRepository<List<Post>> = PostRepository()
    // TODO: save subreddit and sortBy preference to savedstate
    private var subreddit = "grandorder"
    private var sortBy = "new"

    private val _data: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            viewModelScope.launch {
                it.value = get(10)
            }
        }
    }

    // Encapsulate access to mutable LiveData using backing property
    // with LiveData
    override val data: LiveData<List<Post>> get() = _data
    var loading = false

    // api call must be in a separat thread
    fun loadMoreData(count: Int) {
        viewModelScope.launch {
            _data.value = get(count)
            loading = false
        }
    }

    fun saveData() {
       state.set(KEY_SUBREDDIT, subreddit)
    }

    fun getSavedData() = state[KEY_SUBREDDIT] ?: ""

    // call to the api
    private suspend fun get(limit: Int) =
        withContext(Dispatchers.IO) {
            /* perform network IO here */
            repo.getData("$url$subreddit/$sortBy.json?limit=$limit")
        }

    companion object {
        private const val url = "https://www.reddit.com/r/"
        private const val KEY_SUBREDDIT = "KEY_SUBREDDIT"
    }
}