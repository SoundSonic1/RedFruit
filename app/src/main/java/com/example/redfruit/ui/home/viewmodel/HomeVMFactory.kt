package com.example.redfruit.ui.home.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.redfruit.data.api.IRepository
import com.example.redfruit.data.model.Post

/**
 * Factory for HomeViewModel
 */
class HomeVMFactory(
    private val defaultSub: String,
    private val repo: IRepository<List<Post>>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
   override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(defaultSub, repo, handle) as T
    }
}