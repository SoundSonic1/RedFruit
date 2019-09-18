package com.example.redfruit.ui.home.fragment.childfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.redfruit.R
import com.example.redfruit.databinding.SubredditAboutFragmentBinding
import com.example.redfruit.ui.shared.SubredditAboutViewModel

class SubredditAboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SubredditAboutFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.subreddit_about_fragment, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        val viewModel by activityViewModels<SubredditAboutViewModel>()
        binding.viewModel = viewModel

        return binding.root
    }

}
