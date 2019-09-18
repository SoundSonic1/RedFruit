package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.api.IPostsRepository
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.base.IViewModel
import com.example.redfruit.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Control logic of the SubredditPostsFragment
 * @property _subReddit to be shown in the Fragment
 * @property repo handles the api calls
 */
class HomePostsViewModel(
    private var _subReddit: String,
    private val sortBy: SortBy,
    private val repo: IPostsRepository<List<Post>>
) : ViewModel(), IViewModel<List<Post>> {

    val subReddit get() = _subReddit

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _data: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            viewModelScope.launch {
                isLoading.value = true
                it.value = get(Constants.LIMIT)
                isLoading.value = false
            }
        }
    }

    /**
     * Encapsulate access to mutable LiveData using backing property
     * with LiveData
     */
    override val data: LiveData<List<Post>> get() = _data

    // api call must be in a separat thread
    fun loadMoreData(count: Int) {
        isLoading.value = true
        viewModelScope.launch {
            _data.value = get(count)
            isLoading.value = false
        }
    }

    fun changeSub(sub: String) {
        _subReddit = sub
        loadMoreData(Constants.LIMIT)
    }

    fun refreshSub() {
        repo.clearData()
        loadMoreData(Constants.LIMIT)
    }

    // call to the api
    private suspend fun get(count: Int) =
        withContext(Dispatchers.IO) {
            /* perform network IO here */
            repo.getData(_subReddit.toLowerCase(Locale.ENGLISH), sortBy, count)
        }
}