package com.example.redfruit.ui.comments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.repositories.ICommentsRepository
import com.example.redfruit.ui.base.ILiveData
import com.example.redfruit.ui.comments.groupie.CommentExpandableGroup
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val repo: ICommentsRepository
) : ViewModel(), ILiveData<List<CommentExpandableGroup>> {

    private val _isLoading = MutableLiveData<Boolean>(false)

    private val _data: MutableLiveData<List<CommentExpandableGroup>> by lazy {
        MutableLiveData<List<CommentExpandableGroup>>().also {
            _isLoading.value = true
            viewModelScope.launch {
                it.value = repo.getComments(limit).map {
                    CommentExpandableGroup(it)
                }
                _isLoading.value = false
            }
        }
    }

    override val data: LiveData<List<CommentExpandableGroup>> get() = _data

    val isLoading: LiveData<Boolean> get() = _isLoading

    fun refreshComments() {
        _isLoading.value = true
        viewModelScope.launch {
            _data.value = repo.getComments(limit).map {
                CommentExpandableGroup(it)
            }
            _isLoading.value = false
        }
    }

    companion object {
        private const val limit = 200
    }
}
