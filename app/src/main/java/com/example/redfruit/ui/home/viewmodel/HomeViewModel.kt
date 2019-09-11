package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.*
import com.example.redfruit.data.api.IRepository
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.base.IViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Control logic of the HomeFragment
 * @property _subReddit to be shown in the Fragment
 * @property repo handles the api calls
 * @property state used to get saved configurations
 */
class HomeViewModel(
    private var _subReddit: String,
    private val repo: IRepository<List<Post>>,
    private val state: SavedStateHandle
) : ViewModel(), IViewModel<List<Post>> {

    // TODO: save _subReddit and sortBy preference to savedstate
    val subReddit get() = _subReddit
    private var sortBy = SortBy.new

    private val _data: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            viewModelScope.launch {
                it.value = get(limit)
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

    fun changeSub(sub: String) {
        _subReddit = sub.toLowerCase(Locale.ENGLISH)
        loadMoreData(limit)
    }

    fun saveData() = state.set(KEY_SUBREDDIT, _subReddit)


    fun getSavedData() {
        _subReddit = state[KEY_SUBREDDIT] ?: _subReddit
    }

    // call to the api
    private suspend fun get(count: Int) =
        withContext(Dispatchers.IO) {
            /* perform network IO here */
            repo.getData(_subReddit, sortBy.toString(), count)
        }

    companion object {
        private const val KEY_SUBREDDIT = "KEY_SUBREDDIT"
        private const val limit = 15
    }
}