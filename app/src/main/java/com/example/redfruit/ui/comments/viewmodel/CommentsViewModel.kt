package com.example.redfruit.ui.comments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redfruit.data.api.ICommentsRepository
import com.example.redfruit.ui.base.IViewModel
import com.example.redfruit.ui.comments.groupie.CommentExpandableGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsViewModel(
    private val repo: ICommentsRepository
) : ViewModel(), IViewModel<List<CommentExpandableGroup>> {

    private val _data: MutableLiveData<List<CommentExpandableGroup>> by lazy {
        MutableLiveData<List<CommentExpandableGroup>>().also {
           viewModelScope.launch {
               it.value = getData()
           }
        }
    }

    override val data: LiveData<List<CommentExpandableGroup>> get() = _data

    private suspend fun getData() = withContext(Dispatchers.IO) {
        repo.getComments(200).map {
            CommentExpandableGroup(it)
        }
    }
}
