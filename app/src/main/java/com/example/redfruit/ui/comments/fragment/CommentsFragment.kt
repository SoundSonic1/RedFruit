package com.example.redfruit.ui.comments.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.redfruit.R
import com.example.redfruit.ui.comments.viewmodel.CommentsViewModel
import dagger.android.support.DaggerFragment

class CommentsFragment : DaggerFragment() {

    private lateinit var viewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        // TODO: Use the ViewModel
        return inflater.inflate(R.layout.comments_fragment, container, false)
    }

}
