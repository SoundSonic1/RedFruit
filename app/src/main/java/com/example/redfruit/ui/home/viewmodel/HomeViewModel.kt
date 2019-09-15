package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.api.IRepository
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.base.IViewModel
import com.example.redfruit.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Control logic of the HomeFragment
 * @property _subReddit to be shown in the Fragment
 * @property repo handles the api calls
 */
class HomeViewModel(
    private var _subReddit: String,
    private val sortBy: SortBy,
    private val repo: IRepository<List<Post>>
) : ViewModel(), IViewModel<List<Post>> {

    // TODO: save _subReddit and sortBy preference to savedstate
    val subReddit get() = _subReddit

    private val _data: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            viewModelScope.launch {
                it.value = get(Constants.LIMIT)
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
        loadMoreData(Constants.LIMIT)
    }

    // call to the api
    private suspend fun get(count: Int) =
        withContext(Dispatchers.IO) {
            /* perform network IO here */
            repo.getData(_subReddit, sortBy.name, count)
        }
}