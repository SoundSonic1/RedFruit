package com.example.redfruit.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.api.SubredditAboutRepository
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.base.IViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * ViewModel used for communication between Fragments and Activity
 * For now, we specify the type at compile time
 */
class SubredditAboutViewModel(subreddit: String,
                              sorting: SortBy,
                              private val repo: SubredditAboutRepository
) : ViewModel(), IViewModel<SubredditAbout> {

    private val _data: MutableLiveData<SubredditAbout> by lazy {
        MutableLiveData<SubredditAbout>().also {
            viewModelScope.launch {
                it.value = getSubredditAbout(subreddit)
            }
        }
    }

    private val _sortBy: MutableLiveData<SortBy> = MutableLiveData(sorting)

    override val data: LiveData<SubredditAbout> get() = _data

    val sortBy: LiveData<SortBy> get() = _sortBy

    /**
     * Only set lower case
     */
    fun setSub(sub: String) {
        viewModelScope.launch {
            _data.value = getSubredditAbout(sub)
        }
    }

    fun setSort(sortBy: SortBy) {
        _sortBy.value = sortBy
    }

    private suspend fun getSubredditAbout(sub: String) = withContext(Dispatchers.IO) {
        repo.getData(sub.toLowerCase(Locale.ENGLISH))
    }
}