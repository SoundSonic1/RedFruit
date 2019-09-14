package com.example.redfruit.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.redfruit.data.api.IRepository
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy

/**
 * Factory for HomeViewModel
 * @property defaultSub subreddit name
 * @property repo used for data fetching
 */
class HomeVMFactory(
    private val defaultSub: String,
    private val sortBy: SortBy,
    private val repo: IRepository<List<Post>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(defaultSub, sortBy, repo) as T
    }
}
