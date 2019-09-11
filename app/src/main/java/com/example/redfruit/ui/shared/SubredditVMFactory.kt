package com.example.redfruit.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubredditVMFactory(private val subreddit: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SubredditViewModel(subreddit) as T
    }
}