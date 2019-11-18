package com.example.redfruit.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.enumeration.SortPostBy
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.ui.base.ILiveData
import java.util.Locale
import kotlinx.coroutines.launch

/**
 * ViewModel used for communication between Fragments and Activity
 * For now, we specify the type at compile time
 */
class SubredditAboutViewModel(
    subreddit: String,
    sorting: SortPostBy,
    private val repo: SubredditAboutRepository
) : ViewModel(), ILiveData<SubredditAbout> {

    private val _data: MutableLiveData<SubredditAbout> by lazy {
        MutableLiveData<SubredditAbout>().also {
            viewModelScope.launch {
                it.value = repo.getData(subreddit.toLowerCase(Locale.ENGLISH))
            }
        }
    }

    private val _sortPostBy: MutableLiveData<SortPostBy> = MutableLiveData(sorting)

    private val _subreddits: MutableLiveData<List<SubredditAbout>> = MutableLiveData()

    override val data: LiveData<SubredditAbout> get() = _data

    val sortPostBy: LiveData<SortPostBy> get() = _sortPostBy

    val subreddits: LiveData<List<SubredditAbout>> get() = _subreddits

    /**
     * Sets the new subreddit about if the page exists and returns true
     * in that case.
     */
    suspend fun setSub(sub: String): Boolean {
        val subredditAbout = repo.getData(sub.toLowerCase(Locale.ENGLISH))
        return if (subredditAbout != null) {
            _data.value = subredditAbout
            true
        } else {
            false
        }
    }

    fun setSort(sortPostBy: SortPostBy) {
        _sortPostBy.value = sortPostBy
    }

    /**
     * Finds subreddits based on query input from SearchView
     */
    fun findSubreddits(query: String) {
        viewModelScope.launch {
            _subreddits.value = repo.findSubreddits(query, limit)
        }
    }

    /**
     * Change subreddit on click
     */
    fun changeSubOnClick(pos: Int): Boolean {

        val subs = subreddits.value?.getOrNull(pos) ?: return false

        _data.value = subs

        return true
    }

    companion object {
        private const val limit = 6
    }
}
