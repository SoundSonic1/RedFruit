package com.example.redfruit.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.ui.base.IViewModel
import kotlinx.coroutines.launch
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
                it.value = repo.getData(subreddit.toLowerCase(Locale.ENGLISH))
            }
        }
    }

    private val _sortBy: MutableLiveData<SortBy> = MutableLiveData(sorting)

    override val data: LiveData<SubredditAbout> get() = _data

    val sortBy: LiveData<SortBy> get() = _sortBy

    /**
     * Sets the new subreddit about if the page exists and returns true
     * in that case.
     */
    suspend fun setSub(sub: String): Boolean {
        val subredditAbout = repo.getData(sub.toLowerCase(Locale.ENGLISH))
        if (subredditAbout != null) {
            _data.value = subredditAbout
            return true
        } else {
            return false
        }
    }

    fun setSort(sortBy: SortBy) {
        _sortBy.value = sortBy
    }
}