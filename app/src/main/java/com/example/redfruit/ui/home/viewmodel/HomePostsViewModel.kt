package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortPostBy
import com.example.redfruit.data.repositories.IPostsRepository
import com.example.redfruit.ui.base.ILiveData
import com.example.redfruit.util.Constants
import kotlinx.coroutines.launch

/**
 * Control logic of the SubredditPostsFragment
 * @property _subReddit to be shown in the Fragment
 * @property repo handles the api calls
 */
class HomePostsViewModel(
    private var _subReddit: String,
    var sortPostBy: SortPostBy,
    private val repo: IPostsRepository
) : ViewModel(), ILiveData<List<Post>> {

    val subReddit get() = _subReddit

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _data: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>().also {
            viewModelScope.launch {
                isLoading.value = true
                it.value = repo.loadMorePosts(
                    _subReddit, sortPostBy, Constants.LIMIT
                )
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
        viewModelScope.launch {
            isLoading.value = true
            try {
                _data.value =
                    repo.loadMorePosts(_subReddit, sortPostBy, count)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun changeSub(sub: String) {
        _subReddit = sub
        loadMoreData(Constants.LIMIT)
    }

    fun refreshSub() {
        repo.clearPosts()
        loadMoreData(Constants.LIMIT)
    }
}
